package com.darvin.weatherapp;


class Weather {
    private String main;
    private String description;
    private String icon;
    private String temp;
    private String location;

    Weather() {

    }

    Weather(String main, String description, String icon, String temp, String location) {
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.temp = temp;
        this.location = location;
    }

    String getMain() {
        return main;
    }

    String getDescription() {
        return description;
    }

    String getIcon() {
        return icon;
    }

    String getTemp() {
        return temp;
    }

    String getLocation() {
        return location;
    }
}
