package io.dope.kafka.monitor.service;

import io.dope.kafka.monitor.dto.ProducerDTO;
import org.apache.kafka.clients.admin.DescribeProducersResult;
import org.apache.kafka.clients.admin.ProducerState;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class ProducerService {
    private final KafkaClient kafkaClient = new KafkaClient();

    public Collection<ProducerDTO> producers() {
        Collection<ProducerDTO> result = new HashSet<>();

        Map<TopicPartition, DescribeProducersResult.PartitionProducerState> producers = kafkaClient.producers();

        for (Map.Entry<TopicPartition, DescribeProducersResult.PartitionProducerState> entry : producers.entrySet()) {
            List<ProducerState> states = entry.getValue().activeProducers();
            for (ProducerState state : states) {
                result.add(new ProducerDTO(state.producerId(), state.producerEpoch(), state.lastTimestamp()));
            }
        }

        return result;
    }
}
