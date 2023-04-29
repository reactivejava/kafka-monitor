package io.dope.kafka.monitor.controller;

import io.dope.kafka.monitor.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/topics")
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
        return "createTopic";
    }

    @PostMapping("/create")
    public String createTopic(
            @RequestParam("name") String name,
            @RequestParam("partition") int partition,
            @RequestParam("factor") int factor,
            Model model
    ) {
        service.createTopic(name, partition, factor);
        return "redirect:/topics";
    }
}
