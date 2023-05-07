package io.dope.kafka.monitor.service;

import io.dope.kafka.monitor.dto.ConsumerGroupDTO;
import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ConsumerGroupService {
    private final KafkaClient kafkaClient = new KafkaClient();

    public Collection<ConsumerGroupDTO> consumerGroups() {
        Collection<ConsumerGroupListing> groupListings = kafkaClient.consumerGroups();
        return groupListings.stream()
                .map(cg ->
                        new ConsumerGroupDTO(
                                cg.groupId(),
                                cg.isSimpleConsumerGroup(),
                                cg.state().isEmpty() ? null : cg.state().get().name()
                        )
                )
                .collect(Collectors.toSet());
    }

    public void deleteConsumerGroup(String group) {
        kafkaClient.deleteConsumerGroups(group);
    }
}
