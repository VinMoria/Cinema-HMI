package com.example.cinema.vo;

public class BoxOfficeVO {

    private int moiveId;

    private String name;

    private Integer BoxOffice;

    public BoxOfficeVO(int moiveId,String name,double BoxOffice){
        this.setName(name);
        this.setBoxOffice(new Integer((int)BoxOffice));
        this.setMoiveId(moiveId);
    }


    public int getMoiveId() {
        return moiveId;
    }

    public void setMoiveId(int moiveId) {
        this.moiveId = moiveId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int  getBoxOffice() {
        return BoxOffice.intValue();
    }

    public void setBoxOffice(double boxOffice) {
        this.BoxOffice = new Integer((int) boxOffice);
    }
}
