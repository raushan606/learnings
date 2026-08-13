package com.raushan.service;

import com.raushan.model.Expense;
import com.raushan.model.Money;
import com.raushan.model.SplitType;
import com.raushan.model.Transaction;
import com.raushan.model.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class ExpenseManager {
    private final List<User> users = new ArrayList<>();
    private final Map<User, Map<User, Long>> ledger = new LinkedHashMap<>();
    private final List<Expense> expenses = new ArrayList<>();

    public User register(String name) {
        User u = new User("u" + (users.size() + 1), name);
        users.add(u);
        ledger.put(u, new LinkedHashMap<>());
        return u;
    }

    public void addExpense(String desc, SplitType type, long amountCents, User paidBy, List<User> participants, List<Long> values) {
        if (amountCents <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        Map<User, Long> shares = SplitStrategyFactory.of(type)
                .computeShares(amountCents, participants, values);
        expenses.add(new Expense(desc, type, amountCents, paidBy, shares));
        for (var e : shares.entrySet()) {
            if (!e.getKey().equals(paidBy) && e.getValue() > 0) {
                addDebt(e.getKey(), paidBy, e.getValue());
            }
        }
    }

    public void settleUp(User payer, User payee, long cents) {
        adjust(payer, payee, -cents);
        adjust(payee, payer, cents);
    }

    private void addDebt(User from, User to, long amount) {  // 'from' owes 'to'
        adjust(from, to, amount);
        adjust(to, from, -amount);
    }

    private void adjust(User a, User b, long delta) {
        ledger.get(a).merge(b, delta, Long::sum);
    }

    public void showBalances() {
        boolean any = false;
        for (User a : users)
            for (var e : ledger.get(a).entrySet())
                if (e.getValue() > 0) {                    // print each pair once
                    System.out.println("  " + a + " owes " + e.getKey() + " " + Money.fmt(e.getValue()));
                    any = true;
                }
        if (!any) System.out.println("  (all settled up)");
    }

    Map<User, Long> netBalances() {
        Map<User, Long> net = new LinkedHashMap<>();
        for (User u : users) {
            long owes = ledger.get(u).values().stream().mapToLong(Long::longValue).sum();
            net.put(u, -owes);                             // positive = is owed (creditor)
        }
        return net;
    }

    public void showNet() {
        netBalances().forEach((u, bal) -> {
            String state = bal > 0 ? "is owed" : bal < 0 ? "owes" : "is settled";
            System.out.println("  " + u + " " + state + " " + Money.fmt(Math.abs(bal)));
        });
    }

    public List<Transaction> simplifyDebts() {
        PriorityQueue<Bal> creditors = new PriorityQueue<>((x, y) -> Long.compare(y.amount, x.amount));
        PriorityQueue<Bal> debtors = new PriorityQueue<>((x, y) -> Long.compare(y.amount, x.amount));
        netBalances().forEach((u, bal) -> {
            if (bal > 0) creditors.add(new Bal(u, bal));
            else if (bal < 0) debtors.add(new Bal(u, -bal));   // store magnitude
        });
        List<Transaction> result = new ArrayList<>();
        while (!creditors.isEmpty() && !debtors.isEmpty()) {
            Bal c = creditors.poll(), d = debtors.poll();
            long amt = Math.min(c.amount, d.amount);
            result.add(new Transaction(d.user, c.user, amt));  // debtor pays creditor
            if (c.amount - amt > 0) creditors.add(new Bal(c.user, c.amount - amt));
            if (d.amount - amt > 0) debtors.add(new Bal(d.user, d.amount - amt));
        }
        return result;
    }

    private record Bal(User user, long amount) {
    }
}




