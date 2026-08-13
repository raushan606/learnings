package com.raushan;

import com.raushan.model.Money;
import com.raushan.model.SplitType;
import com.raushan.model.Transaction;
import com.raushan.model.User;
import com.raushan.service.ExpenseManager;
import com.raushan.service.SplitStrategyFactory;

import java.util.List;

public class Splitter {
    public static void main(String[] args) {
        ExpenseManager mgr = new ExpenseManager();
        User alice = mgr.register("Alice");
        User bob = mgr.register("Bob");
        User charlie = mgr.register("Charlie");

        // Dinner ₹300 split EQUALLY among all three, paid by Alice
        mgr.addExpense("Dinner", SplitType.EQUAL, 30000, alice,
                List.of(alice, bob, charlie), null);
        // Cab ₹150 split EQUALLY between Bob and Charlie, paid by Bob
        mgr.addExpense("Cab", SplitType.EQUAL, 15000, bob,
                List.of(bob, charlie), null);
        // Hotel ₹1000 split 50/30/20 by PERCENT, paid by Charlie
        mgr.addExpense("Hotel", SplitType.PERCENT, 100000, charlie,
                List.of(alice, bob, charlie), List.of(50L, 30L, 20L));

        System.out.println("=== Balances (netted, who owes whom) ===");
        mgr.showBalances();
        System.out.println("\n=== Net position per person ===");
        mgr.showNet();
        System.out.println("\n=== Simplified settlement (fewest transfers) ===");
        for (Transaction t : mgr.simplifyDebts()) System.out.println("  " + t);

        System.out.println("\n=== Alice pays Charlie ₹300 (settle up) ===");
        mgr.settleUp(alice, charlie, 30000);
        mgr.showBalances();

        System.out.println("\n=== Rounding: ₹100 split equally 3 ways ===");
        SplitStrategyFactory.of(SplitType.EQUAL)
                .computeShares(10000, List.of(alice, bob, charlie), null)
                .forEach((u, c) -> System.out.println("  " + u + " owes " + Money.fmt(c)));
    }
}