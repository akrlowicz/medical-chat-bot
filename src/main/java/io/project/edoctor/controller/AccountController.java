package io.project.edoctor.controller;

import io.project.edoctor.model.entity.User;
import io.project.edoctor.model.entity.UserData;
import io.project.edoctor.model.forms.RegistrationForm;
import io.project.edoctor.repository.UserDataRepository;
import io.project.edoctor.service.UserDataService;
import io.project.edoctor.service.UserService;
import io.project.edoctor.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserDataService userDataService;

    @GetMapping("/index")
    public String showMain(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") @Valid RegistrationForm userForm, BindingResult bindingResult, Model model) {


        userService.isRegistered(userForm.getEmail());


        if (bindingResult.hasErrors()) {
            return "registration";
        }

        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());


        UserData userData = new UserData();
        userData.setBirth(userForm.getBirth());
        userData.setGender(userForm.getGender());
        userData.setHeight(userForm.getHeight());
        userData.setWeight(userForm.getWeight());
        userData.setUserData(user);


        userService.save(user);
        userDataService.saveUserData(userData);
        return "redirect:/login";
    }
}
