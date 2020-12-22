package com.example.cinema.blImpl.sales;

import com.example.cinema.CinemaApplication;
import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.AudiencePrice;
import com.example.cinema.vo.*;
import com.example.cinema.po.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CinemaApplication.class)
public class TicketServiceImplTest {
    @Autowired
    TicketService ticketService;
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

    @Test
    public void addTicket() {
    }

    @Test
    public void completeTicket() {
//        List <Integer> id = new ArrayList<>();
//        id.add(412);
//        OrderVO orderVO = new OrderVO();
//        orderVO.setCouponId(9);
//        orderVO.setTicketId(id);
//        ResponseVO responseVO = ticketService.completeTicket(orderVO);
//        assertEquals(java.util.Optional.ofNullable(((AudiencePriceVO) responseVO.getContent()).getPrice()),5);
    }

    @Test
    public void getBySchedule() {
    }

    @Test
    public void getTicketByUser() {
    }

    @Test
    public void completeByVIPCard() {

    }

    @Test
    public void cancelTicket() {
    }
}
