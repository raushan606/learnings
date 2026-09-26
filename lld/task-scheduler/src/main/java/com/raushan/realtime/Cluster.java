package com.raushan.realtime;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cluster {
    private final String id;
    private final int totalCpu;
    private final int totalRam;
    private int availableCpu;
    private int availableRam;
    private final Lock resoruceLock;

    public Cluster(String id, int totalCpu, int totalRam, int availableCpu, int availableRam) {
        this.id = id;
        this.totalCpu = totalCpu;
        this.totalRam = totalRam;
        this.availableCpu = totalCpu;
        this.availableRam = totalRam;
        resoruceLock = new ReentrantLock();
    }

    public boolean isResourceAvailable(int jobCpu, int jobRam) {
        return availableCpu >= jobCpu && availableRam >= jobRam;
    }

    public boolean allocateResource(int jobCpu, int jobRam) {
        try {
            System.out.println("Trying to acquire Lock for Cluster " + id + " at " + System.currentTimeMillis());
            if (resoruceLock.tryLock(5, TimeUnit.MILLISECONDS)) {
                try {
                    if (isResourceAvailable(jobCpu, jobRam)) {
                        availableCpu -= jobCpu;
                        availableRam -= jobRam;
                        return true;
                    } else {
                        System.out.println("Resource not available for cluster " + id + " at " + System.currentTimeMillis());
                    }
                } finally {
                    resoruceLock.unlock();
                }
            } else {
                System.out.println("Resource not available for cluster " + id + " at " + System.currentTimeMillis());
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void deallocateResource(int jobCpu, int jobRam) {
        resoruceLock.lock();
        try {
            availableCpu += jobCpu;
            availableRam += jobRam;
        } finally {
            resoruceLock.unlock();
        }
    }

    public String getId() {
        return this.id;
    }
}
