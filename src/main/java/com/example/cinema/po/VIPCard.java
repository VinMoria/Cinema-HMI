package com.example.cinema.po;
import java.sql.Timestamp;


public class VIPCard {
    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡id
     */
    private int id;

    /**
     * 会员卡余额
     */
    private double balance;

    /**
     * 办卡日期
     */
    private Timestamp joinDate;


    private int vipCardTypeId;

    /**
     * 会员卡种类
     **/

    public VIPCard() {

    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }


    public int getVipCardTypeId() {
        return vipCardTypeId;
    }

    public void setVipCardTypeId(int vipCardTypeId) {
        this.vipCardTypeId = vipCardTypeId;
    }
}
