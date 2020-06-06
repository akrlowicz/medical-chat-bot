package io.project.edoctor.controller;

import io.project.edoctor.exception.InvalidEmailOrPassword;
import io.project.edoctor.model.GeneratePdf;
import io.project.edoctor.model.entity.User;
import io.project.edoctor.model.entity.UserData;
import io.project.edoctor.model.entity.UserDiagnosis;
import io.project.edoctor.model.entity.UserInterview;
import io.project.edoctor.model.forms.ChangeInfoForm;
import io.project.edoctor.model.forms.ChangePasswordForm;
import io.project.edoctor.service.InterviewService;
import io.project.edoctor.service.UserDataService;
import io.project.edoctor.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class UserViewController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserDataService userDataService;

    @Autowired
    InterviewService interviewService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/userview")
    public String showUserView(Model model, Authentication auth) {

        User user = userService.findByEmail(auth.getName());
        UserData userData = user.getUserData();

        model.addAttribute("user",user);
        model.addAttribute("userdata",userData);
        model.addAttribute("userForm", new ChangeInfoForm());
        model.addAttribute("passwordForm", new ChangePasswordForm());
        return "userview";
    }

    @PostMapping("/updateinfo")
    public String updateUserInfo(Authentication auth, @ModelAttribute("userForm") @Valid ChangeInfoForm userForm, BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors()) {
            User user = userService.findByEmail(auth.getName());
            UserData userData = user.getUserData();

            model.addAttribute("user",user);
            model.addAttribute("userdata",userData);
            model.addAttribute("userForm", userForm);
            model.addAttribute("passwordForm", new ChangePasswordForm());
            return "userview";
        }

        User user = userService.findByEmail(auth.getName());
        UserData userData = user.getUserData();


        if (!userForm.getName().isEmpty())
            userData.setName(userForm.getName());
        if (userForm.getHeight() != null)
            userData.setHeight(userForm.getHeight());
        if (userForm.getWeight() != null)
            userData.setWeight(userForm.getWeight());


        userDataService.saveUserData(userData);

        return "redirect:/userview";
    }

    @PostMapping("/updatepassword")
    public String updateUserPassword(Authentication auth, @ModelAttribute("passwordForm") @Valid ChangePasswordForm passwordForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            User user = userService.findByEmail(auth.getName());
            UserData userData = user.getUserData();

            model.addAttribute("user",user);
            model.addAttribute("userdata",userData);
            model.addAttribute("userForm", new ChangeInfoForm());
            model.addAttribute("passwordForm", passwordForm);
            return "userview";
        }

        String email = auth.getName();
        User user = userService.findByEmail(email);

        if (bCryptPasswordEncoder.matches(passwordForm.getOldPassword(), user.getPassword())){
            if  (passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword()))
                user.setPassword(passwordForm.getNewPassword());
        } else
            throw new InvalidEmailOrPassword("Old password does not match");

        userService.save(user);

        return "redirect:/logout";

    }


    @GetMapping(value = "/diagnosispdf")
    public ResponseEntity<InputStreamResource> openDiagnosis(@RequestParam Integer id, Model model, Authentication auth) throws Exception {

        User user = userService.findByEmail(auth.getName());

        UserInterview userInterview = interviewService.findById(id);
        List<UserDiagnosis> userDiagnosisList = userInterview.getUserDiagnoses();

        String fileName = userInterview.getDate().toString() + "-" + userInterview.getId();

        ByteArrayInputStream bis = GeneratePdf.generatePdf(user, userDiagnosisList);

        InputStreamResource streamResource = new InputStreamResource(bis);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline;attachment;filename="+ fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(streamResource);

    }


}
