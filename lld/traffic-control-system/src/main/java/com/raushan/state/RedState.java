package com.raushan.state;

import com.raushan.TrafficLight;
import com.raushan.enums.LightColor;

public class RedState implements SignalState {
    @Override
    public void handle(TrafficLight context) {
        context.setColor(LightColor.RED);
        context.setNextState(new GreenState());
    }
}
