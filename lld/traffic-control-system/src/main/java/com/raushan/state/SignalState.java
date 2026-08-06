package com.raushan.state;

import com.raushan.TrafficLight;

public interface SignalState {
    void handle(TrafficLight context);
}
