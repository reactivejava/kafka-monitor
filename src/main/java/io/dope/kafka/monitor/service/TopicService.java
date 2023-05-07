package io.dope.kafka.monitor.service;

import io.dope.kafka.monitor.dto.TopicDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TopicService {
    private final KafkaClient kafkaClient = new KafkaClient();

    public Set<TopicDTO> getTopics() {
        return kafkaClient.listTopics();
    }

    public void createTopic(String name, int partition, int factor) {
        kafkaClient.createTopic(new NewTopic(name, partition, (short) factor));
    }

    public void deleteTopic(String topic) {
        kafkaClient.deleteTopic(topic);
    }
}
