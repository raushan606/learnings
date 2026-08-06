package com.raushan.state;

import com.raushan.IntersectionController;
import com.raushan.enums.Direction;
import com.raushan.enums.LightColor;

public class EastWestGreenState implements IntersectionState {
    @Override
    public void handle(IntersectionController context) throws InterruptedException {
        System.out.println("INTERSECTION STATE: East-West direction is GREEN. North-South direction is RED.");
        context.getLight(Direction.EAST).startGreen();
        context.getLight(Direction.WEST).startGreen();
        context.getLight(Direction.NORTH).setColor(LightColor.RED);
        context.getLight(Direction.SOUTH).setColor(LightColor.RED);

        Thread.sleep(context.getGreenDuration());

        context.getLight(Direction.EAST).transition();
        context.getLight(Direction.WEST).transition();

        Thread.sleep(context.getYellowDuration());

        context.getLight(Direction.EAST).transition();
        context.getLight(Direction.WEST).transition();

        context.setState(new NorthSouthGreenState());

    }
}
