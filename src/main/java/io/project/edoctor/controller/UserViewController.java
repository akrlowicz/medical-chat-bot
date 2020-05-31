package io.project.edoctor.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.project.edoctor.exception.InvalidEmailOrPassword;
import io.project.edoctor.model.entity.User;
import io.project.edoctor.model.entity.UserData;
import io.project.edoctor.model.entity.UserDiagnosis;
import io.project.edoctor.model.entity.UserInterview;
import io.project.edoctor.model.forms.ChangeInfoForm;
import io.project.edoctor.model.forms.ChangePasswordForm;
import io.project.edoctor.model.forms.RegistrationForm;
import io.project.edoctor.service.InterviewService;
import io.project.edoctor.service.UserDataService;
import io.project.edoctor.service.UserServiceImpl;
import org.hibernate.annotations.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

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

            return "redirect:/userview";
        }

        User user = userService.findByEmail(auth.getName());
        UserData userData = user.getUserData();


        if (!userForm.getName().isEmpty())
            userData.setName(userForm.getName());
        if (userForm.getHeight() != null)
            userData.setHeight(userForm.getHeight());
        if (userForm.getWeight() != null)
            userData.setWeight(userForm.getWeight());
        if (!userForm.getEmail().isEmpty()) {
            user.setEmail(userForm.getEmail());
            userService.save(user);
            return "redirect:/logout";
        }

        userDataService.saveUserData(userData);

        return "redirect:/userview";
    }

    @PostMapping("/updatepassword")
    public String updateUserPassword(Authentication auth,@ModelAttribute("passwordForm") @Valid ChangePasswordForm passwordForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "userview";
        }

        User user = userService.findByEmail(auth.getName());

        if (bCryptPasswordEncoder.matches(passwordForm.getOldPassword(), user.getPassword())){
            if  (passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
                user.setPassword(passwordForm.getNewPassword());
            } else
                throw new InvalidEmailOrPassword("Passwords are not the same");
        } else
            throw new InvalidEmailOrPassword("Old password does not match");

        userService.save(user);

        return "redirect:/logout";

    }

    @PostMapping("/save")
    public String saveDiagnosis(@RequestParam Integer id,Model model, Authentication auth) throws Exception {

        User user = userService.findByEmail(auth.getName());

        UserInterview userInterview = interviewService.findById(id);
        List<UserDiagnosis> userDiagnosisList = userInterview.getUserDiagnoses();

        String filename = userInterview.getDate().toString() + "-" + userInterview.getId();

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));

        document.open();
        Font bigFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,16, BaseColor.BLACK);
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.BLACK);
        Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        Paragraph title = new Paragraph("E-Doctor", bigFont); title.setAlignment(Element.ALIGN_CENTER);
        Paragraph title2 = new Paragraph("Results for preliminary diagnosis", bigFont); title2.setAlignment(Element.ALIGN_CENTER);

        addEmptyLine(title2,2);

        Paragraph chunk = new Paragraph("Patient: " + user.getUserData().getName() + ", age: " + user.getUserData().getAge() + ", " + user.getUserData().getGender(), font);
        Paragraph chunk2 = new Paragraph("Height: " + user.getUserData().getHeight() + "cm, weight: " + user.getUserData().getWeight() + "kg", font);
        Paragraph chunk3 = new Paragraph("Please note that this is not a medical diagnosis. Consult your doctor to confirm your results.", smallFont);

        addEmptyLine(chunk2,2);

        PdfPTable table = new PdfPTable(2);
        addTableHeader(table);
        for (UserDiagnosis userDiagnosis: userDiagnosisList) {
            table.addCell(userDiagnosis.getName());
            table.addCell(userDiagnosis.getProbability().toString());
        }

        //String p = "jetbrains://idea/navigate/reference?project=e-doctor&path=static/img/pic2.jpg";
        //Path path = Paths.get("src/main/resources/static/img/pic2.jpg").toURI());

        //Image img = Image.getInstance(path.toString());

        document.add(title);
        document.add(title2);
        document.add(chunk);
        document.add(chunk2);
        document.add(table); document.add(new Paragraph(" "));
        document.add(chunk3);
       // document.add(img);
        document.close();

        return "redirect:/userview";
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Illness", "Probability")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
