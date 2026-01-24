package com.raushan.abstractfactory;

public class MacosCheckbox implements Checkbox{
    @Override
    public void paint() {
        System.out.println("Painting Macos Checkbox");
    }
    @Override
    public void onSelect() {
        System.out.println("Macos Checkbox selected");
    }
}
