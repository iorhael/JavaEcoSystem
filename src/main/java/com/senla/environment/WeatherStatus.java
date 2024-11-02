package com.senla.environment;

public enum WeatherStatus {
    COLD, MODERATE, WARM;

    public String toSaveFormat(){
        return "weather " + this.toString();
    }
}
