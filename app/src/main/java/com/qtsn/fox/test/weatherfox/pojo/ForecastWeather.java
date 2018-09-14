package com.qtsn.fox.test.weatherfox.pojo;

public class ForecastWeather {

    private Integer low;
    private Integer high;
    private Integer skycodeday;
    private String skytextday;
    private String date;
    private String day;
    private String shortday;
    private Integer precip;
    private String degType;

    public ForecastWeather() {
    }

    public Integer getLow() {
        return low;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public Integer getSkycodeday() {
        return skycodeday;
    }

    public void setSkycodeday(Integer skycodeday) {
        this.skycodeday = skycodeday;
    }

    public String getSkytextday() {
        return skytextday;
    }

    public void setSkytextday(String skytextday) {
        this.skytextday = skytextday;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getShortday() {
        return shortday;
    }

    public void setShortday(String shortday) {
        this.shortday = shortday;
    }

    public Integer getPrecip() {
        return precip;
    }

    public void setPrecip(Integer precip) {
        this.precip = precip;
    }

    public String getDegType() {
        return degType;
    }

    public void setDegType(String degType) {
        this.degType = degType;
    }
}
