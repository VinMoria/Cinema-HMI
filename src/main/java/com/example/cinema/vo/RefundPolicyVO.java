package com.example.cinema.vo;

import com.example.cinema.po.RefundPolicy;

public class RefundPolicyVO {
    private boolean canRefund;

    private double refundRate;

    public boolean isCanRefund() {
        return canRefund;
    }

    public void setCanRefund(boolean canRefund) {
        this.canRefund = canRefund;
    }

    public double getRefundRate() {
        return refundRate;
    }

    public void setRefundRate(double refundRate) {
        this.refundRate = refundRate;
    }

    public RefundPolicy getPO(){
        RefundPolicy refundPolicy = new RefundPolicy();
        refundPolicy.setRefundRate(refundRate);
        refundPolicy.setRefundType(canRefund?1:0);
        refundPolicy.setSchduleId(-1);
        return refundPolicy;
    }
}
