package com.example.cinema.data.sales;

import com.example.cinema.po.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Mapper
public interface TicketMapper {

    int insertTicket(Ticket ticket);

    int insertTickets(List<Ticket> tickets);

    void deleteTicket(int ticketId);

    void updateTicketState(@Param("ticketId") int ticketId, @Param("state") int state);

    List<Ticket> selectTicketsBySchedule(@Param("scheduleId") int scheduleId);

    List<Ticket> selectAllTicketsBySchedule(@Param("scheduleId") int scheduleId);

    Ticket selectTicketByScheduleIdAndSeat(@Param("scheduleId") int scheduleId, @Param("column") int columnIndex, @Param("row") int rowIndex);

    Ticket selectTicketById(int id);

    List<Ticket> selectTicketByUser(int userId);

    //添加一个功能：根据输入的日期，返回相应的票的信息
    List<Ticket> selectTicketByDateAndId(@Param("id")int id,  @Param("date") Date date, @Param("nextDate") Date nextDate);

    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredTicket();
}

