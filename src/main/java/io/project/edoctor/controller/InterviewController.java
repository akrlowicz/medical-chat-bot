package io.project.edoctor.controller;

import io.project.edoctor.model.entity.User;
import io.project.edoctor.model.forms.RegistrationForm;
import io.project.edoctor.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InterviewController {

    @Autowired
    UserServiceImpl userService;

    private String botName = "E-doctor";
    private String firstMessage = "Please write down your symptoms, and we'll try to help you with your preliminary diagnosis.";
    private List<Message> messages = new ArrayList<>();

    private String name;
    private String sex;
    private int age;

    @GetMapping("/chat")
    public String showChat(Model model, Authentication auth) {

        messages.clear();
        messages.add(new Message(botName, firstMessage));

        User user = userService.findByEmail(auth.getName());
        name = "You"; // NIE MA W BAZIE DANYCH O IMIENIU I NAZWISKU !!
        sex = user.getUserData().getGender();
        age = calculateAge(user);

        model.addAttribute("userMessage", new Message());
        model.addAttribute("messages", messages);

        return "chat";
    }

    @PostMapping("/chat")
    public String updateMessages(@ModelAttribute("userMessage") @Valid Message userMessage, BindingResult bindingResult, Model model) {
        userMessage.setSender(name);
        messages.add(userMessage);
        model.addAttribute("userMessage", new Message());

        messages.add(new Message(botName, "answer"));
        model.addAttribute("messages", messages);

        return "chat";
    }

    private int calculateAge (User user) {

        LocalDate date = user.getUserData().getBirth();
        LocalDate now = LocalDate.now();

        int ageValue = now.getYear() - date.getYear();

        if (now.getDayOfYear() < date.getDayOfYear()) {
            ageValue = ageValue-1;
        }

        return ageValue;
    }

}
