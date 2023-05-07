package io.dope.kafka.monitor.controller;

import io.dope.kafka.monitor.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/producers")
@Slf4j
public class ProducerController {

    @Autowired
    ProducerService service;

    @GetMapping
    public String producers(Model model) {
        model.addAttribute("producers", service.producers());
        return "producers";
    }
}
