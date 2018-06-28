package com.powerapi.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PowerapiCI {
    public PowerapiCI(double power, long timestamp, String testName, long timeBeginTest, long timeEndTest, long testDuration, double energy) {
        this.power = power;
        this.timestamp = timestamp;
        this.testName = testName;
        this.timeBeginTest = timeBeginTest;
        this.timeEndTest = timeEndTest;
        this.testDuration = testDuration;
        this.energy = energy;
    }

    private double power;
    private long timestamp;
    private String testName;
    private long timeBeginTest;
    private long timeEndTest;
    private long testDuration;
    private double energy;
}
