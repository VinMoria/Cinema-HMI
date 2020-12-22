package com.example.cinema.blImpl.statistics;

import com.example.cinema.bl.statistics.StatisticsService;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.data.management.MovieMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.data.statistics.StatisticsMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author fjj
 * @date 2019/4/16 1:34 PM
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private HallMapper hallMapper;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    MovieMapper movieMapper;

    @Override
    public ResponseVO getScheduleRateByDate(Date date) {
        try{
            Date requireDate = date;
            if(requireDate == null){
                requireDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));

            Date nextDate = getNumDayAfterDate(requireDate, 1);
            return ResponseVO.buildSuccess(movieScheduleTimeList2MovieScheduleTimeVOList(statisticsMapper.selectMovieScheduleTimes(requireDate, nextDate)));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTotalBoxOffice() {
        try {
            return ResponseVO.buildSuccess(movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(statisticsMapper.selectMovieTotalBoxOffice()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    @Override
    public ResponseVO getAudiencePriceSevenDays() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date startDate = getNumDayAfterDate(today, -6);
            List<AudiencePriceVO> audiencePriceVOList = new ArrayList<>();
            for(int i = 0; i < 7; i++){
                AudiencePriceVO audiencePriceVO = new AudiencePriceVO();
                Date date = getNumDayAfterDate(startDate, i);
                audiencePriceVO.setDate(date);
                List<AudiencePrice> audiencePriceList = statisticsMapper.selectAudiencePrice(date, getNumDayAfterDate(date, 1));
                double totalPrice = audiencePriceList.stream().mapToDouble(item -> item.getTotalPrice()).sum();
                audiencePriceVO.setPrice(Double.parseDouble(String.format("%.2f", audiencePriceList.size() == 0 ? 0 : totalPrice / audiencePriceList.size())));
                audiencePriceVOList.add(audiencePriceVO);
            }
            return ResponseVO.buildSuccess(audiencePriceVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /* 上座率参考公式：假设某影城设有n 个电影厅、m 个座位数，相对上座率=观众人次÷放映场次÷m÷n×100%
     * 此方法中运用到的相应的操作数据库的接口和实现请自行定义和实现，相应的结果需要自己定义一个VO类返回给前端
     */
    @Override
    public ResponseVO getMoviePlacingRateByDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date utilDate = null;
        try {
            utilDate = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = new java.sql.Date(utilDate.getTime());
        //要求见接口说明
        //需要返回的数据：需要所有电影的名字；

        //需要的HallService定义的操作：
        //1.返回影院的影厅数和座位数
        //2.需要ticket提供总票数来表明观众人数
        //3.需要scheduleservice提供放映场次
        try {
            Calendar calendar = Calendar. getInstance();
            calendar.setTime(date);
//            calendar.set(Calendar. HOUR_OF_DAY, 0);
//            calendar.set(Calendar. MINUTE, 0);
//            calendar.set(Calendar. SECOND, 0);
//            calendar.set(Calendar. MILLISECOND, 0);
            Date date1 = calendar.getTime();
            calendar.add(Calendar. DAY_OF_MONTH, 1);
            Date date2 = calendar.getTime();

            List<MoviePlacingRateVO> moviePlacingRateVOList = new ArrayList<>();

            //助教sql取得的list，以name为group
            List<MovieScheduleTime> MovieList = statisticsMapper.selectMovieScheduleTimes(date1, date2);


            //遍历这个list(以name为group)，得到当天上映的所有的电影的ID
            for (MovieScheduleTime movieScheduleTime : MovieList) {
                MoviePlacingRateVO moviePlacingRateVO = new MoviePlacingRateVO();
                moviePlacingRateVO.setId(movieScheduleTime.getMovieId());
                moviePlacingRateVO.setName(movieScheduleTime.getName());
                //计算seats=m*n
                List<Hall> HallList= hallMapper.selectAllHall();
                int seats = 0;
                for (Hall hall : HallList){
                    seats += hall.getRow()*hall.getColumn();
                }


                //需要根据movieID和date得到一个scheduleID的list
                //遍历这个scheduleID的list，然后去ticket库中进行查找并计数
                int ticketnums = 0;
                List<ScheduleItem> ScheduleList = scheduleMapper.selectScheduleByIdAndDate(movieScheduleTime.getMovieId(),date1,date2);
                for (ScheduleItem i : ScheduleList){
                    ticketnums += ticketMapper.selectAllTicketsBySchedule(i.getId()).size();
                }

                //根据ticketMapper得到特定date和特定ID的票数
                double placeRate = (double) ticketnums / (double) (seats*(movieScheduleTime.getTime().intValue()));


                moviePlacingRateVO.setPlaceRate(placeRate);
                moviePlacingRateVOList.add(moviePlacingRateVO);
            }


            return ResponseVO.buildSuccess(moviePlacingRateVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    /** 此方法中运用到的相应的操作数据库的接口和实现请自行定义和实现，相应的结果需要自己定义一个VO类返回给前端
     * 获取最近days天内，最受欢迎的movieNum个电影(可以简单理解为最近days内票房越高的电影越受欢迎)
     * @param days
     * @param movieNum
     * @return
     * 前端需要得到的数据：返回这些电影的列表包括它们各自的票房
     */
    @Override
    public ResponseVO getPopularMovies(int days, int movieNum) {
        try {
            Date today = new Date();
            today.getTime();
            Date dateBefore = getNumDayAfterDate(today, -days);

            List<ScheduleItem> scheduleItemList = scheduleMapper.selectScheduleByDate(dateBefore, today);

            List<BoxOfficeVO> boxOfficeVOList = new ArrayList<>();

            for (ScheduleItem scheduleItem : scheduleItemList) {
                String movieName = movieMapper.selectMovieById(scheduleItem.getMovieId()).getName();
                List<Ticket> TicketOfSchedule = ticketMapper.selectTicketsBySchedule(scheduleItem.getId());
                double BoxOfSchedule = scheduleItem.getFare() * (TicketOfSchedule.size());
                if (boxOfficeVOList.size() != 0) {
                    boolean MovieIsInList = false;
                    for (BoxOfficeVO boxOfSchedule : boxOfficeVOList) {
                        if (boxOfSchedule.getMoiveId() == scheduleItem.getMovieId()) {
                            boxOfSchedule.setBoxOffice(boxOfSchedule.getBoxOffice() + BoxOfSchedule);
                            MovieIsInList = true;
                        }
                    }
                    if (!MovieIsInList) {
                        BoxOfficeVO tempboxOfficeVo = new BoxOfficeVO(scheduleItem.getMovieId(),movieName,BoxOfSchedule);
                        boxOfficeVOList.add(tempboxOfficeVo);
                    }
                }else {
                    BoxOfficeVO tempboxOfficeVo = new BoxOfficeVO(scheduleItem.getMovieId(),movieName,BoxOfSchedule);
                    boxOfficeVOList.add(tempboxOfficeVo);
                }
            }


            Collections.sort(boxOfficeVOList,Comparator.comparing(BoxOfficeVO::getBoxOffice));

            Collections.reverse(boxOfficeVOList);

            if(boxOfficeVOList.size()>movieNum) {
                return (ResponseVO.buildSuccess(boxOfficeVOList.subList(0, movieNum)));
            }

            return ResponseVO.buildSuccess(boxOfficeVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    /**
     * 获得num天后的日期
     * @param oldDate
     * @param num
     * @return
     */
    public Date getNumDayAfterDate(Date oldDate, int num){
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(oldDate);
        calendarTime.add(Calendar.DAY_OF_YEAR, num);
        return calendarTime.getTime();
    }





    /**
     * @param movieScheduleTimeList
     * @return
     * 输入的参数是一个 电影排片时间 的列表
     * 返回的时候，返回了一个 由 MovieScheduleTimeVO 实例 组成的列表
     */
    private List<MovieScheduleTimeVO> movieScheduleTimeList2MovieScheduleTimeVOList(List<MovieScheduleTime> movieScheduleTimeList){
        List<MovieScheduleTimeVO> movieScheduleTimeVOList = new ArrayList<>();
        for(MovieScheduleTime movieScheduleTime : movieScheduleTimeList){
            movieScheduleTimeVOList.add(new MovieScheduleTimeVO(movieScheduleTime));
        }
        return movieScheduleTimeVOList;
    }


    private List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(List<MovieTotalBoxOffice> movieTotalBoxOfficeList){
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOList = new ArrayList<>();
        for(MovieTotalBoxOffice movieTotalBoxOffice : movieTotalBoxOfficeList){
            movieTotalBoxOfficeVOList.add(new MovieTotalBoxOfficeVO(movieTotalBoxOffice));
        }
        return movieTotalBoxOfficeVOList;
    }
}
