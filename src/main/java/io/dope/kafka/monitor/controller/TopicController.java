package io.dope.kafka.monitor.controller;

import io.dope.kafka.monitor.model.Topic;
import io.dope.kafka.monitor.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/topics")
@Slf4j
public class TopicController {

    @Autowired
    TopicService service;

    @GetMapping
    public String topics(Model model) {
        model.addAttribute("topics", service.getTopics());

        return "topics";
    }

    @GetMapping("/create")
    public String createTopicForm(Model model) {
        model.addAttribute("topic", new Topic());
        return "create-topic";
    }

    @PostMapping("/create")
    public String createTopic(@ModelAttribute Topic topic, Model model) {
        log.info("Create topic[name={}, partitions={}, factor={}]", topic.getName(), topic.getPartitions(), topic.getReplicationFactor());
        service.createTopic(topic.getName(), topic.getPartitions(), topic.getReplicationFactor());
        model.addAttribute("topic", topic);
        return "redirect:/topics";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteTopic(@RequestParam(name = "topic") String topic, Model model) {
        log.info("Delete topic[name={}]", topic);
        service.deleteTopic(topic);
        model.addAttribute("topic", topic);
        return "redirect:/topics";
    }
}
