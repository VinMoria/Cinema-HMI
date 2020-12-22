package com.example.cinema.po;

import com.example.cinema.vo.VIPCardTypeVO;

/**
 *
 * **/
public class VIPCardType {

    private int id;

    private String name;

    private String description;

    /**
     * 购卡金额
     * **/
    private double price;

    /**
     * 状态：
     * 0：不可购买
     * 1：可购买
     * **/
    private int state;

    /**
     *优惠类型：
     * 1：购买影票时的打折优惠:
     *  a.discountRate为折扣，discount = 0.7时表示7折
     *  b.无打折优惠时discountRate为1
     * 2：充值时的满减优惠
     *  a.无满减优惠时discountAmount为0
     * **/

    private double discountRate;

    private double targetAmount;

    private double discountAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VIPCardTypeVO getVO(){
        VIPCardTypeVO vipCardTypeVO = new VIPCardTypeVO();
        vipCardTypeVO.setId(id);
        vipCardTypeVO.setPrice(price);
        vipCardTypeVO.setDiscountAmount(discountAmount);
        vipCardTypeVO.setDiscountRate(discountRate);
        vipCardTypeVO.setTargetAmount(targetAmount);
        vipCardTypeVO.setState(state);
        vipCardTypeVO.setDescription(description);
        if(discountRate == 1){
            vipCardTypeVO.setHasDiscountRate(false);
        }
        else {
            vipCardTypeVO.setHasDiscountRate(true);
        }
        if(discountAmount == 0){
            vipCardTypeVO.setHasDiscountAomount(false);
        }else {
            vipCardTypeVO.setHasDiscountAomount(true);
        }
        return vipCardTypeVO;
    }

    @Override
    public String toString() {
        return "VIPCardType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", discountRate=" + discountRate +
                ", targetAmount=" + targetAmount +
                ", discountAmount=" + discountAmount +
                '}';
    }
}
