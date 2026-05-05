package com.raushan;

import com.raushan.generics.Box;
import com.raushan.generics.OrderedPair;
import com.raushan.generics.Pair;
import com.raushan.generics.Util;

import java.util.List;
import java.util.Objects;

class User {
    private final String name = "Raushan";
}
public class Main {
    public static void main(String[] args) throws Exception {
        User user = new User();
        var field = User.class.getDeclaredField("name");
        field.setAccessible(true);
        field.set(user, "Kumar");  // mutating final field
        System.out.println(field.get(user)); // Kumar
    }
}