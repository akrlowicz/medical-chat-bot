package io.project.edoctor.model.forms;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;


public class RegistrationForm {


    @NotBlank(message = "Enter name")
    private String name;

    @Email
    @NotBlank(message = "Enter email")
    private String email;

    @NotBlank(message = "Enter password")
    @Size(min = 6, message = "Password too short")
    private String password;

    @NotBlank(message = "Choose gender")
    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private LocalDate birth;

    private Integer height;

    private Integer weight;

    public RegistrationForm() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {

        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
