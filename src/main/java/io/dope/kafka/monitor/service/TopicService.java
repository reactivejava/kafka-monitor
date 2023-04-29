package io.dope.kafka.monitor.service;

import io.dope.kafka.monitor.model.Topic;
import io.dope.kafka.monitor.util.Utils;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class TopicService {
    private static final AdminClient adminClient = Utils.adminClient();

    public Set<Topic> getTopics() {
        Set<Topic> result = new HashSet<>();

        try {
            Collection<String> allTopics = adminClient.listTopics().names().get(5000, TimeUnit.SECONDS);

            allTopics.forEach(t -> result.add(new Topic(t, 10, 3)));

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createTopic(NewTopic topic) {
        CreateTopicsResult topicsResult = adminClient.createTopics(Collections.singleton(topic));
        try {
            topicsResult.all().get(5000, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
