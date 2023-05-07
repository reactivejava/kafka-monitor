package io.dope.kafka.monitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrokerDTO {
    private final int brokerId;
    private final String host;
}
