package io.dope.kafka.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Broker {
    private final int brokerId;
    private final String host;
}
