package com.raushan.observer;

import com.raushan.enums.Direction;
import com.raushan.enums.LightColor;

public class CentralMonitor implements TrafficObserver {
    @Override
    public void update(int intId, Direction direction, LightColor color) {
        System.out.println("Intersection ID: " + intId + ", Direction: " + direction + ", Light Color: " + color);
    }
}
