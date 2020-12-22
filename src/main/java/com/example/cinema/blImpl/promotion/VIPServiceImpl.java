package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.promotion.VIPCardTypeMapper;
import com.example.cinema.po.VIPCardType;
import com.example.cinema.vo.VIPCardChargeForm;
import com.example.cinema.po.VIPCard;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPCardForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService {
    @Autowired
    VIPCardMapper vipCardMapper;
    @Autowired
    VIPCardTypeMapper vipCardTypeMapper;

    @Override
    public ResponseVO addVIPCard(VIPCardForm vipCardForm) {
        int userId = vipCardForm.getUserId();
        int vipTypeId = vipCardForm.getVipCardTypeId();
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setVipCardTypeId(vipTypeId);
        vipCard.setBalance(0);
        try {
            int id = vipCardMapper.insertOneCard(vipCard);
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO charge(VIPCardChargeForm vipCardChargeForm) {

        VIPCard vipCard = vipCardMapper.selectCardById(vipCardChargeForm.getVipId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }
        VIPCardType vipCardType = vipCardTypeMapper.selectVIPCardTypeById(vipCard.getVipCardTypeId());
        double balance =  vipCard.getBalance();
        if(vipCardType.getDiscountAmount()!=0){
            balance += (vipCardChargeForm.getAmount()/vipCardType.getTargetAmount())*vipCardType.getDiscountAmount();
        }
        else {
            balance += vipCardChargeForm.getAmount();
        }
        try {
            vipCard.setBalance(balance);
            vipCardMapper.updateCardBalance(vipCardChargeForm.getVipId(), vipCard.getBalance());
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if (vipCard == null) {
                return ResponseVO.buildFailure("用户卡不存在");
            }
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**----------------vipCardType---------------**/

    @Override
    public ResponseVO addVIPCardType(VIPCardType vipCardType) {
        try {
            vipCardTypeMapper.insertVIPCardType(vipCardType);
            return ResponseVO.buildSuccess(vipCardType);
        } catch (Exception e) {
            return ResponseVO.buildFailure("添加失败");
        }
    }

    @Override
    public ResponseVO updateVIPCardType(VIPCardType vipCardType) {
        try {
            vipCardTypeMapper.updateVIPCardType(vipCardType);
            return ResponseVO.buildSuccess(vipCardTypeMapper.selectVIPCardTypeById(vipCardType.getId()));
        } catch (Exception e) {
            return ResponseVO.buildFailure("修改失败");
        }
    }

    @Override
    public ResponseVO getVIPCardTypeById(int id) {
        try{
            VIPCardType vipCardType = vipCardTypeMapper.selectVIPCardTypeById(id);
            return ResponseVO.buildSuccess(vipCardType);
        } catch (Exception e) {
            return ResponseVO.buildFailure("获取失败");
        }
    }

    @Override
    public ResponseVO getAllVIPCardType() {
        try {
            List<VIPCardType> vipCardTypeList = vipCardTypeMapper.getAllVipCardType();
            return ResponseVO.buildSuccess(vipCardTypeList);
        } catch (Exception e) {
            return ResponseVO.buildFailure("获取全部失败");
        }
    }

    @Override
    public ResponseVO deleteVIPCardType(int id) {
        try{
            vipCardTypeMapper.deleteVIPCardType(id);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            return ResponseVO.buildFailure("删除失败");
        }
    }


}
