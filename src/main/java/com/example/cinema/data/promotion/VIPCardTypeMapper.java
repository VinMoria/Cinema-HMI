package com.example.cinema.data.promotion;

import com.example.cinema.po.VIPCardType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VIPCardTypeMapper {
    void insertVIPCardType(VIPCardType vipCardType);

    VIPCardType selectVIPCardTypeById(int id);

    void updateVIPCardType(VIPCardType vipCardType);

    void deleteVIPCardType(int id);

    List<VIPCardType> getAllVipCardType();
}