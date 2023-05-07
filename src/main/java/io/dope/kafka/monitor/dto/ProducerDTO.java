package io.dope.kafka.monitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerDTO {
    private long producerId;
    private int producerEpoch;
    private long lastTimestamp;
}
