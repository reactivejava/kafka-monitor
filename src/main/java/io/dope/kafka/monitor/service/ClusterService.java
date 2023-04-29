package io.dope.kafka.monitor.service;

import io.dope.kafka.monitor.model.Cluster;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.Node;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static io.dope.kafka.monitor.config.Default.BOOTSTRAP;

@Service
public class ClusterService {
    public Cluster getClusterInfo() {
        try (AdminClient adminCli = createAdminCli()) {
            String clusterId = adminCli.describeCluster().clusterId().get(5000, TimeUnit.SECONDS);
            Node controlledNode = adminCli.describeCluster().controller().get(5000, TimeUnit.SECONDS);

            return new Cluster(clusterId, controlledNode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AdminClient createAdminCli() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP);
        return AdminClient.create(props);
    }
}
