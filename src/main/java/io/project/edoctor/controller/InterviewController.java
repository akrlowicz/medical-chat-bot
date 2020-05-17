package io.project.edoctor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InterviewController {

    private String firstMessage = "Please write down your symptoms, and we'll try to help you with your preliminary diagnosis.";
    private List<String> messages = new ArrayList<>();

    @GetMapping("/chat")
    public String showChat(Model model) {

        messages.clear();

        model.addAttribute("firstMessage", firstMessage);
        model.addAttribute("messages", messages);

        return "chat";
    }

}
