package com.example.cinema.test;

import com.example.cinema.CinemaApplication;
import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardTypeMapper;
import com.example.cinema.po.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CinemaApplication.class)
public class VIPServiceTest {

    @Autowired
    VIPService vipService;
    @Autowired
    VIPCardTypeMapper vipCardTypeMapper;

    private VIPCardType getVIPCardType(){
//        VIPCardType vipCardType = new VIPCardType();
//        vipCardType.setName("白金会员卡");
//        vipCardType.setDescription("7折会员卡无满减");
//        vipCardType.setPrice(200);
//        vipCardType.setDiscountAmount(0);
//        vipCardType.setDiscountRate(0.7);
//        vipCardType.setTargetAmount(0);
//        vipCardType.setState(1);
//        return  vipCardType;
        return null;
    }


    @Test
    public void testDatabase(){
//        VIPCardType vipCardType = getVIPCardType();
//        System.out.println(vipCardType);
//        vipCardTypeMapper.insertVIPCardType(vipCardType);
    }

    @Test
    public void test1(){
//        VIPCardType vipCardType = (VIPCardType) vipService.getVIPCardTypeById(3).getContent();
//        System.out.println(vipCardType.toString());
    }

    @Test
    public void test2(){
//        List<VIPCardType> vipCardTypes = (List<VIPCardType>) vipService.getAllVIPCardType().getContent();
//        for (VIPCardType i:vipCardTypes){
//            System.out.println(i);
//        }
    }

    @Test
    public void test3(){
//        VIPCardType vipCardType = getVIPCardType();
//        vipCardType = (VIPCardType) vipService.addVIPCardType(vipCardType).getContent();
//        vipCardType.setState(0);
//        System.out.println(vipCardType);
//        vipService.updateVIPCardType(vipCardType);
    }




}
