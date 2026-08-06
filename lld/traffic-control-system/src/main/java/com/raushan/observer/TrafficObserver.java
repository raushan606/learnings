package com.raushan.observer;

import com.raushan.enums.Direction;
import com.raushan.enums.LightColor;

public interface TrafficObserver {
    void update(int intId, Direction direction, LightColor color);
}
