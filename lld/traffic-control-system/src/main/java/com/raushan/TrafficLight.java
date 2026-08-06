package com.raushan;

import com.raushan.enums.Direction;
import com.raushan.enums.LightColor;
import com.raushan.observer.CentralMonitor;
import com.raushan.observer.TrafficObserver;
import com.raushan.state.GreenState;
import com.raushan.state.RedState;
import com.raushan.state.SignalState;

import java.util.List;

public class TrafficLight {
    private final Direction direction;
    private LightColor currentColor;
    private SignalState currentState;
    private SignalState nextState;
    private final List<TrafficObserver> observerList = List.of(new CentralMonitor());
    private final int intId;

    public TrafficLight(Direction direction, int intId) {
        this.direction = direction;
        this.intId = intId;
        this.currentState = new RedState();
        this.currentState.handle(this);
    }

    public void startGreen() {
        this.currentState = new GreenState();
        this.currentState.handle(this);
    }

    public void transition() {
        this.currentState = this.nextState;
        this.currentState.handle(this);
    }

    public void setColor(LightColor color) {
        this.currentColor = color;
        notifyObservers();
    }

    public void setNextState(SignalState nextState) {
        this.nextState = nextState;
    }

    public LightColor getCurrentColor() {
        return currentColor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void addObserver(TrafficObserver observer) {
        observerList.add(observer);
    }

    private void notifyObservers() {
        for (TrafficObserver observer : observerList) {
            observer.update(intId, direction, currentColor);
        }
    }
}
