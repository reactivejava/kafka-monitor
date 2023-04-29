package io.dope.kafka.monitor.service;

import io.dope.kafka.monitor.model.Topic;
import io.dope.kafka.monitor.util.Utils;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class KafkaClient {
    private static final Logger LOG = LoggerFactory.getLogger(TopicService.class);
    private AdminClient adminClient;

    public Set<Topic> getTopics() {
        Set<Topic> result = new HashSet<>();
        try {
            adminClient = Utils.adminClient();
            Collection<String> allTopics = adminClient.listTopics().names().get(5, TimeUnit.SECONDS);

            allTopics.forEach(t -> result.add(new Topic(t, 10, 3)));

            return result;
        } catch (Exception e) {
            LOG.error("ERROR: {}", e.getMessage());
        } finally {
            adminClient.close();
        }

        return result;
    }

    public void createTopic(NewTopic topic) {
        try {
            adminClient = Utils.adminClient();
            CreateTopicsResult topicsResult = adminClient.createTopics(Collections.singleton(topic));
            topicsResult.all().get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOG.error("ERROR: {}", e.getMessage());
        } finally {
            adminClient.close();
        }
    }

    public void deleteTopic(String topic) {
        try {
            adminClient = Utils.adminClient();
            DeleteTopicsResult topicsResult = adminClient.deleteTopics(Collections.singletonList(topic));
            topicsResult.all().get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOG.error("ERROR: {}", e.getMessage());
        } finally {
            adminClient.close();
        }
    }
}
