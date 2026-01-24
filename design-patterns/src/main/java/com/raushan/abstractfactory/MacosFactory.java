package com.raushan.abstractfactory;

public class MacosFactory implements GUIFactory{
    @Override
    public Button createButton() {
        return new MacosButton();
    }
    @Override
    public Checkbox createCheckbox() {
        return new MacosCheckbox();
    }
}
