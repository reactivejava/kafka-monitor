package io.dope.kafka.monitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.kafka.common.Node;

@Data
@AllArgsConstructor
public class QuorumInfoDTO {
    private final String clusterId;
    private final Node controllerNode;
}
