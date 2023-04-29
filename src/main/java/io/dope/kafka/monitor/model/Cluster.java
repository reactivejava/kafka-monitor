package io.dope.kafka.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.kafka.common.Node;

@Data
@AllArgsConstructor
public class Cluster {
    private final String clusterId;
    private final Node controllerNode;
}
