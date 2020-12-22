package com.example.cinema.po;

public class RefundPolicy {

    private int id;

    /**
     * 0为不可退款
     * 1为可退款
     * **/
    private int refundType;

    private double refundRate;

    /**
     * 场次id
     * 若为-1表示默认退票策略**/
    private int scheduleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRefundType() {
        return refundType;
    }

    public void setRefundType(int refundType) {
        this.refundType = refundType;
    }

    public double getRefundRate() {
        return refundRate;
    }

    public void setRefundRate(double refundRate) {
        this.refundRate = refundRate;
    }

    public int getSchduleId() {
        return scheduleId;
    }

    public void setSchduleId(int schduleId) {
        this.scheduleId = schduleId;
    }
}
