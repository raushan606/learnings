package com.raushan.abstractfactory;

public class MacosButton implements Button{
    @Override
    public void paint() {
        System.out.println("Painting Macos Button");
    }
    @Override
    public void onClick() {
        System.out.println("Macos Button clicked");
    }
}
