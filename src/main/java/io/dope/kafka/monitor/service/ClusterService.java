package io.dope.kafka.monitor.service;

import io.dope.kafka.monitor.dto.ClusterDTO;
import io.dope.kafka.monitor.dto.QuorumInfoDTO;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.QuorumInfo;
import org.apache.kafka.common.Node;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static io.dope.kafka.monitor.config.Default.BOOTSTRAP;

@Service
public class ClusterService {
    private final KafkaClient kafkaClient = new KafkaClient();
    public ClusterDTO clusterInfo() {
        try (AdminClient adminCli = createAdminCli()) {
            String clusterId = adminCli.describeCluster().clusterId().get(5000, TimeUnit.SECONDS);
            Node controlledNode = adminCli.describeCluster().controller().get(5000, TimeUnit.SECONDS);

            return new ClusterDTO(clusterId, controlledNode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String quorumInfo() {
        QuorumInfo info = kafkaClient.quorumInfo();
        return info.toString();
    }

    private AdminClient createAdminCli() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP);
        return AdminClient.create(props);
    }
}
