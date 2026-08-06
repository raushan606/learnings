package com.raushan.state;

import com.raushan.TrafficLight;
import com.raushan.enums.LightColor;

public class YellowState implements SignalState {
    @Override
    public void handle(TrafficLight context) {
        context.setColor(LightColor.YELLOW);
        context.setNextState(new RedState());
    }
}
