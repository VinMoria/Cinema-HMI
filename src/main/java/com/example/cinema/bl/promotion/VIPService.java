package com.example.cinema.bl.promotion;

import com.example.cinema.po.VIPCardType;
import com.example.cinema.vo.VIPCardChargeForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPCardForm;


/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    ResponseVO addVIPCard(VIPCardForm vipCardForm);

    ResponseVO getCardById(int id);

    ResponseVO charge(VIPCardChargeForm vipCardChargeForm);

    ResponseVO getCardByUserId(int userId);

    ResponseVO addVIPCardType(VIPCardType vipCardType);

    ResponseVO updateVIPCardType(VIPCardType vipCardType);

    ResponseVO getVIPCardTypeById(int id);

    ResponseVO getAllVIPCardType();

    ResponseVO deleteVIPCardType(int id);
}
