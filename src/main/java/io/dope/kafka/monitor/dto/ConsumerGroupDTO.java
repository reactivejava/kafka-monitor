package io.dope.kafka.monitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumerGroupDTO {
    private String groupId;
    private boolean isSimple;
    private String state;
}
