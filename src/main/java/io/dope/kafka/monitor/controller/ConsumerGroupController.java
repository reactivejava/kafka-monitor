package io.dope.kafka.monitor.controller;

import io.dope.kafka.monitor.service.ConsumerGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/consumers")
@Slf4j
public class ConsumerGroupController {

    @Autowired
    ConsumerGroupService service;

    @GetMapping
    public String consumerGroups(Model model) {
        model.addAttribute("consumers", service.consumerGroups());
        return "consumers";
    }

    @PostMapping("/delete")
    public String consumerGroups(@RequestParam String group, Model model) {
        log.info("Delete consumer group[name={}]", group);
        service.deleteConsumerGroup(group);
        return "redirect:/consumers";
    }
}
