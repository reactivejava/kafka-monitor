package io.dope.kafka.monitor.util;

import io.dope.kafka.monitor.config.Default;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;

import java.util.Properties;

public class Utils {
    private Utils() {}

    public static AdminClient adminClient() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, Default.BOOTSTRAP);
        return AdminClient.create(props);
    }
}
