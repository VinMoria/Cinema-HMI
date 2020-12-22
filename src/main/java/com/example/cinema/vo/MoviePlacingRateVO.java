package com.example.cinema.vo;

public class MoviePlacingRateVO {
    /**
     * 电影id
     */
    private Integer id;
    /**
     * 电影名称
     */
    private String name;
    /**
     * 电影上座率
     */
    private double placeRate;


    public double getPlaceRate() {
        return placeRate;
    }

    public void setPlaceRate(double placeRate) {
        this.placeRate = placeRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
