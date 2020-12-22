package com.example.cinema.vo;

import java.util.*;

public class OrderVO {
    private List<Integer> TicketId;

    private int couponId;

    public List<Integer> getTicketId() {
        return TicketId;
    }

    public void setTicketId(List<Integer> ticketId) {
        TicketId = ticketId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }
}
