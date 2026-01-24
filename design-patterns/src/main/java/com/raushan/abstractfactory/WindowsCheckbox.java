package com.raushan.abstractfactory;

public class WindowsCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Painting Windows Checkbox");
    }
    @Override
    public void onSelect() {
        System.out.println("Windows Checkbox selected");
    }
}
