package io.dope.kafka.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Topic {
    private String name;
    private int partitions;
    private int replicationFactor;
}
