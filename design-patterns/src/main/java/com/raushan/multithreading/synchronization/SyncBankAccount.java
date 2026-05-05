package com.raushan.multithreading.synchronization;

public class SyncBankAccount {
    private int balance = 0;

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    public synchronized void withdraw(int amount) {
        if (balance > amount)
            balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public class Bank {
        public static void main(String[] args) {
            SyncBankAccount account = new SyncBankAccount();

            Thread t1 = new Thread(() -> {
                    account.deposit(100);
            });

            Thread t2 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    account.withdraw(1);
                }
            });

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Final Balance: " + account.getBalance());
        }
    }
}
