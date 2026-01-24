package com.raushan.abstractfactory;

public class WindowsButton implements Button{
    @Override
    public void paint() {
        System.out.println("Painting Windows Button");
    }
    @Override
    public void onClick() {
        System.out.println("Windows Button clicked");
    }
}
