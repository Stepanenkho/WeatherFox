package com.qtsn.fox.test.weatherfox.pojo;

public class CurrentWeather {

    private Integer temperature;
    private Integer skycode;
    private String skytext;
    private String date;
    private String observationtime;
    private String observationpoint;
    private Integer feelslike;
    private Integer humidity;
    private String winddisplay;
    private String day;
    private String shortday;
    private String windspeed;
    private String imageUrl;
    private String degType;

    public CurrentWeather() {
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getSkycode() {
        return skycode;
    }

    public void setSkycode(Integer skycode) {
        this.skycode = skycode;
    }

    public String getSkytext() {
        return skytext;
    }

    public void setSkytext(String skytext) {
        this.skytext = skytext;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObservationtime() {
        return observationtime;
    }

    public void setObservationtime(String observationtime) {
        this.observationtime = observationtime;
    }

    public String getObservationpoint() {
        return observationpoint;
    }

    public void setObservationpoint(String observationpoint) {
        this.observationpoint = observationpoint;
    }

    public Integer getFeelslike() {
        return feelslike;
    }

    public void setFeelslike(Integer feelslike) {
        this.feelslike = feelslike;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public String getWinddisplay() {
        return winddisplay;
    }

    public void setWinddisplay(String winddisplay) {
        this.winddisplay = winddisplay;
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

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDegType() {
        return degType;
    }

    public void setDegType(String degType) {
        this.degType = degType;
    }
}
