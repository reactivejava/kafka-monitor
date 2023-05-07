package io.dope.kafka.monitor.service;

import io.dope.kafka.monitor.dto.TopicDTO;
import io.dope.kafka.monitor.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.apache.kafka.clients.admin.DescribeProducersResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.QuorumInfo;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclBindingFilter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class KafkaClient {
    private AdminClient adminClient;

    public Set<TopicDTO> listTopics() {
        Set<TopicDTO> result = new HashSet<>();
        try {
            adminClient = Utils.adminClient();
            Collection<String> allTopics = adminClient.listTopics().names().get(5, SECONDS);
            Map<String, TopicDescription> descriptionMap = adminClient.describeTopics(allTopics).allTopicNames().get(5, SECONDS);

            for (Map.Entry<String, TopicDescription> entry : descriptionMap.entrySet()) {
                List<TopicPartitionInfo> partitions = entry.getValue().partitions();
                result.add(new TopicDTO(entry.getKey(), partitions.size(), partitions.get(0).replicas().size()));
            }

            return result;
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage());
        } finally {
            adminClient.close();
        }

        return result;
    }

    public void createTopic(NewTopic topic) {
        try {
            adminClient = Utils.adminClient();
            adminClient.createTopics(Collections.singleton(topic)).all().get(5, SECONDS);
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage());
        } finally {
            adminClient.close();
        }
    }

    public void deleteTopic(String topic) {
        try {
            adminClient = Utils.adminClient();
            adminClient.deleteTopics(Collections.singletonList(topic)).all().get(5, SECONDS);;
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage());
        } finally {
            adminClient.close();
        }
    }

    public Collection<AclBinding> listAcls() {
        try {
            adminClient = Utils.adminClient();

            return adminClient.describeAcls(AclBindingFilter.ANY).values().get(5, SECONDS);
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage());
        } finally {
            adminClient.close();
        }

        return null;
    }

    public QuorumInfo quorumInfo() {
        try {
            adminClient = Utils.adminClient();

            return adminClient.describeMetadataQuorum().quorumInfo().get(5, SECONDS);
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage());
        } finally {
            adminClient.close();
        }

        return null;
    }

    public Collection<ConsumerGroupListing> consumerGroups() {
        try {
            adminClient = Utils.adminClient();

            return adminClient.listConsumerGroups().all().get(5, SECONDS);
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage());
        } finally {
            adminClient.close();
        }

        return Collections.emptyList();
    }

    public void deleteConsumerGroups(String group) {
        try {
            adminClient = Utils.adminClient();
            adminClient.deleteConsumerGroups(Collections.singleton(group)).all().get(5, SECONDS);
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage());
        } finally {
            adminClient.close();
        }
    }

    public Map<TopicPartition, DescribeProducersResult.PartitionProducerState> producers() {
        try {
            Collection<TopicPartition> tp = new HashSet<>();
            adminClient = Utils.adminClient();
            Collection<String> topics = adminClient.listTopics().listings().get(5, SECONDS)
                    .stream()
                    .map(TopicListing::name)
                    .collect(Collectors.toSet());

            Map<String, TopicDescription> descriptionMap = adminClient.describeTopics(topics).allTopicNames().get(5, SECONDS);

            for (Map.Entry<String, TopicDescription> entry : descriptionMap.entrySet()) {
                List<TopicPartitionInfo> partitions = entry.getValue().partitions();

                for (TopicPartitionInfo partition : partitions) {
                    tp.add(new TopicPartition(entry.getKey(), partition.partition()));
                }
            }

            return adminClient.describeProducers(tp).all().get(5, SECONDS);
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage());
        } finally {
            adminClient.close();
        }

        return Collections.emptyMap();
    }
}
