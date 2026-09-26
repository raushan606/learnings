package com.raushan.realtime;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobScheduler {

    private ClusterManager clusterManager;
    private BlockingQueue<Job> jobQueue;
    private ExecutorService executorService;

    public JobScheduler() {
        clusterManager = ClusterManager.getInstance();
        jobQueue = new ArrayBlockingQueue<Job>(100);
        executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.submit(this::executreJob);
        }
    }

    public void submit(Job job) {
        jobQueue.add(job);
    }

    private void executreJob() {
        while (true) {
            try {
                Job job = jobQueue.take();
                Optional<Cluster> cluster = clusterManager.getAvailableCluster(job.requiredCpu(), job.requiredRam());
                if (cluster.isEmpty()) {
                    System.out.println("No cluster available");
                    jobQueue.offer(job);
                    continue;
                } else {
                    startJob(cluster.get(), job);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startJob(Cluster cluster, Job job) {
        long currentTime = System.currentTimeMillis();
        System.out.println("Job " + job.id() + " started on cluster " + cluster.getId() + " at " + currentTime);
        try {
            Thread.sleep(job.executionTime() * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cluster.deallocateResource(job.requiredCpu(), job.requiredRam());
        }
        System.out.println("Job " + job.id() + " completed on cluster " + cluster.getId() + " at " + System.currentTimeMillis());
        System.out.println("Total time taken for job " + job.id() + " is " + (System.currentTimeMillis() - currentTime) / 1000 + "s");
    }

}

