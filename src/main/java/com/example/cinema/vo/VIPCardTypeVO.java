package com.example.cinema.vo;

public class VIPCardTypeVO {
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
    private boolean hasDiscountRate;

    private double discountRate;

    private boolean hasDiscountAomount;

    private double targetAmount;

    private double discountAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isHasDiscountRate() {
        return hasDiscountRate;
    }

    public void setHasDiscountRate(boolean hasDiscountRate) {
        this.hasDiscountRate = hasDiscountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public boolean isHasDiscountAomount() {
        return hasDiscountAomount;
    }

    public void setHasDiscountAomount(boolean hasDiscountAomount) {
        this.hasDiscountAomount = hasDiscountAomount;
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
}
