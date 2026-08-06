package com.raushan.state;

import com.raushan.TrafficLight;
import com.raushan.enums.LightColor;

public class GreenState implements SignalState {
    @Override
    public void handle(TrafficLight context) {
        context.setColor(LightColor.GREEN);
        context.setNextState(new YellowState());
    }
}
