package io.dope.kafka.monitor.controller;

import io.dope.kafka.monitor.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
