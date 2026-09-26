package com.raushan.realtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClusterManager {
    private static volatile ClusterManager instance;
    private List<Cluster> clusters;
    private ClusterManager() {
        clusters = new ArrayList<>();
    }
    public static synchronized ClusterManager getInstance() {
        if (instance == null) {
            instance = new ClusterManager();
        }
        return instance;
    }

    public void addCluster(Cluster cluster) {
        clusters.add(cluster);
    }

    public Optional<Cluster> getAvailableCluster(int cpu, int ram) {
        for (Cluster cluster : clusters) {
            if (cluster.isResourceAvailable(cpu, ram)) {
                cluster.allocateResource(cpu, ram);
                return Optional.of(cluster);
            }
            System.out.println("Cluster " + cluster.getId() + " is full");
        }
        return Optional.empty();
    }
}
