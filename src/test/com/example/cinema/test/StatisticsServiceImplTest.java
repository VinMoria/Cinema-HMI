package com.example.cinema.test;

import com.example.cinema.CinemaApplication;
import com.example.cinema.bl.statistics.StatisticsService;
import com.example.cinema.blImpl.statistics.StatisticsServiceImpl;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.data.statistics.StatisticsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CinemaApplication.class)
public class StatisticsServiceImplTest {
    @Autowired
    StatisticsService statisticsService;

    @Test
    public void getScheduleRateByDate() {
    }

    @Test
    public void getTotalBoxOffice() {
    }

    @Test
    public void getAudiencePriceSevenDays() {
    }

    @Test
    public void getMoviePlacingRateByDate() {
        //statisticsService.getMoviePlacingRateByDate("2020-12-30");
    }

    @Test
    public void getPopularMovies() {
        //statisticsService.getPopularMovies(30, 10);
    }

    @Test
    public void getNumDayAfterDate() {
    }
}
