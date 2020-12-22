package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.data.promotion.ActivityMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.promotion.VIPCardTypeMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Service
public class TicketServiceImpl implements TicketService {

    private final static String BALANCE_INSUFFICIENT = "余额不足";
    private final static String UNABLE_TO_FIND_TICKET = "无法找到影票";
    private final static String FAILURE = "失败";
    private final static String SEAT_OCCUPIED = "座位被占";
    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    HallServiceForBl hallService;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    VIPCardMapper vipCardMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    VIPCardTypeMapper vipCardTypeMapper;

    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
        try {
            List<TicketVO> ticketVOS = new ArrayList<TicketVO>();
            TicketWithCouponVO ticketWithCouponVO = new TicketWithCouponVO();
            ScheduleWithSeatVO scheduleWithSeatVO = (ScheduleWithSeatVO) getBySchedule(ticketForm.getScheduleId()).getContent();
            ScheduleItem scheduleItem = scheduleService.getScheduleItemById(ticketForm.getScheduleId());
            for (SeatForm seat : ticketForm.getSeats()) {
                if (scheduleWithSeatVO.getSeats()[seat.getRowIndex()][seat.getColumnIndex()] == 1) {
                    return ResponseVO.buildFailure(SEAT_OCCUPIED);
                }
                Ticket ticket = new Ticket();
                ticket.setUserId(ticketForm.getUserId());
                ticket.setScheduleId(ticketForm.getScheduleId());
                ticket.setColumnIndex(seat.getColumnIndex());
                ticket.setRowIndex(seat.getRowIndex());
                ticket.setState(0);
                ticketMapper.insertTicket(ticket);
                ticket = ticketMapper.selectTicketByScheduleIdAndSeat(ticketForm.getScheduleId(),seat.getColumnIndex(),seat.getRowIndex());
                ticketVOS.add(ticket.getVO());
            }
            List<Coupon> coupons = couponMapper.selectCouponByUser(ticketForm.getUserId());
            ticketWithCouponVO.setTicketVOList(ticketVOS);
            ticketWithCouponVO.setCoupons(coupons);
            ticketWithCouponVO.setTotal(scheduleItem.getFare()*ticketForm.getSeats().size());
            ticketWithCouponVO.setActivities(activityMapper.selectActivities());
            return ResponseVO.buildSuccess(ticketWithCouponVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(FAILURE);
        }
    }

    @Override
    @Transactional
    public ResponseVO completeTicket(OrderVO orderVO) {
        try {
            Double totalPrice = calculateTotalPrice(orderVO);
            Date date = new Date();
            for (int eachId : orderVO.getTicketId()) {
                ticketMapper.updateTicketState(eachId, 1);
            }
            AudiencePriceVO audiencePriceVO = new AudiencePriceVO();
            audiencePriceVO.setPrice(totalPrice);
            audiencePriceVO.setDate(date);
            return ResponseVO.buildSuccess(audiencePriceVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(FAILURE);
        }
    }

    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
            ScheduleItem schedule = scheduleService.getScheduleItemById(scheduleId);
            Hall hall = hallService.getHallById(schedule.getHallId());
            int[][] seats = new int[hall.getRow()][hall.getColumn()];
            tickets.stream().forEach(ticket -> {
                seats[ticket.getRowIndex()][ticket.getColumnIndex()] = 1;
            });
            ScheduleWithSeatVO scheduleWithSeatVO = new ScheduleWithSeatVO();
            scheduleWithSeatVO.setScheduleItem(schedule);
            scheduleWithSeatVO.setSeats(seats);
            return ResponseVO.buildSuccess(scheduleWithSeatVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(FAILURE);
        }
    }

    @Override
    public ResponseVO getTicketByUser(int userId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketByUser(userId);
            List<TicketVO> ticketVOS = new ArrayList<TicketVO>();
            for (Ticket ticket : tickets) {
                ticketVOS.add(ticket.getVO());
            }
            return ResponseVO.buildSuccess(ticketVOS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(FAILURE);
        }
    }

    @Override
    public ResponseVO getTicketById(int id) {
        try{
            TicketVO ticketVO = ticketMapper.selectTicketById(id).getVO();
            return ResponseVO.buildSuccess(ticketVO);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(FAILURE);
        }
    }


    @Override
    @Transactional
    public ResponseVO completeByVIPCard(OrderVO orderVO) {
        try {
            Double totalPrice = calculateTotalPrice(orderVO);
            int userId = ticketMapper.selectTicketById(orderVO.getTicketId().get(0)).getUserId();
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            VIPCardType vipCardType = vipCardTypeMapper.selectVIPCardTypeById(vipCard.getVipCardTypeId());
            if(vipCardType.getDiscountRate() != 1){
                totalPrice *= vipCardType.getDiscountRate();
            }
            if (vipCard.getBalance() - totalPrice < 0) {
                return ResponseVO.buildFailure(BALANCE_INSUFFICIENT);
            }
            for (int eachId : orderVO.getTicketId()) {
                ticketMapper.updateTicketState(eachId, 1);
            }
            vipCardMapper.updateCardBalance(vipCard.getId(), vipCard.getBalance() - totalPrice);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(FAILURE);
        }
    }

    @Override
    public ResponseVO cancelTicket(List<Integer> id) {
        try {
            for (int eachId : id) {
                if (ticketMapper.selectTicketById(eachId) == null) {
                    return ResponseVO.buildFailure(UNABLE_TO_FIND_TICKET);
                }
                ticketMapper.updateTicketState(eachId,3);
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(FAILURE);
        }
    }

    @Override
    public ResponseVO refund(List<Integer> ticketId) {
        return ResponseVO.buildSuccess("退款成功");
    }

    private Double calculateTotalPrice(OrderVO orderVO)throws Exception{
        List<Integer> id = orderVO.getTicketId();
        int couponId = orderVO.getCouponId();
        Double totalPrice = 0.0;
        Coupon coupon = couponMapper.selectById(couponId);
        int userId = 0;
        for (int eachId : id) {
            Ticket ticket = ticketMapper.selectTicketById(eachId);
            userId = ticket.getUserId();
            ScheduleItem Schedule = scheduleService.getScheduleItemById(ticket.getScheduleId());
            totalPrice += Schedule.getFare();
        }
        if (coupon != null) {
            Date date = new Date();
            if (totalPrice >= coupon.getTargetAmount() &&
                    date.after(coupon.getStartTime()) &&
                    date.before(coupon.getEndTime())) {
                totalPrice -= coupon.getDiscountAmount();
                couponMapper.deleteCouponUser(coupon.getId(),userId);
            }
        }
        if(totalPrice < 0)totalPrice = 0.0;
        return totalPrice;
    }


}