package io.dope.kafka.monitor.service;

import io.dope.kafka.monitor.model.Topic;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TopicService {
    private final KafkaClient kafkaClient = new KafkaClient();

    public Set<Topic> getTopics() {
        return kafkaClient.getTopics();
    }
}
