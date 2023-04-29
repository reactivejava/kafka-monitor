package io.dope.kafka.monitor.controller;

import io.dope.kafka.monitor.model.Cluster;
import io.dope.kafka.monitor.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cluster")
public class ClusterController {

    @Autowired
    ClusterService service;

    @GetMapping
    public String info(Model model) {
        Cluster info = service.getClusterInfo();

        model.addAttribute("clusterId", info.getClusterId());
        model.addAttribute("controllerNode", info.getControllerNode());

        return "cluster";
    }
}
