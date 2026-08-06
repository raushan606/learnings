package com.raushan.state;

import com.raushan.IntersectionController;
import com.raushan.enums.Direction;
import com.raushan.enums.LightColor;

public class NorthSouthGreenState implements IntersectionState {
    @Override
    public void handle(IntersectionController context) throws InterruptedException {
        System.out.println("INTERSECTION: NorthSouthGreenState ");
        context.getLight(Direction.NORTH).startGreen();
        context.getLight(Direction.SOUTH).startGreen();
        context.getLight(Direction.EAST).setColor(LightColor.RED);
        context.getLight(Direction.WEST).setColor(LightColor.RED);

        Thread.sleep(context.getGreenDuration());

        context.getLight(Direction.NORTH).transition();
        context.getLight(Direction.SOUTH).transition();

        Thread.sleep(context.getYellowDuration());

        context.getLight(Direction.NORTH).transition();
        context.getLight(Direction.SOUTH).transition();

        context.setState(new EastWestGreenState());

    }
}
