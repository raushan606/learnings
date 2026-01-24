package com.raushan.abstractfactory;

public class Main {
    public static void main(String[] args) {
        String os = System.getProperty("os.name");
        GUIFactory factory;
        if (os.contains("Windows")) {
            factory = new WindowsFactory();
        } else {
            factory = new MacosFactory();
        }

        Application app = new Application(factory);
        app.render();
    }
}
