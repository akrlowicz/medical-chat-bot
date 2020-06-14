package io.project.edoctor;

import io.project.edoctor.exception.InvalidEmailOrPassword;
import io.project.edoctor.model.entity.User;
import io.project.edoctor.model.entity.UserData;
import io.project.edoctor.model.entity.UserDiagnosis;
import io.project.edoctor.model.entity.UserInterview;
import io.project.edoctor.repository.UserDataRepository;
import io.project.edoctor.repository.UserRepository;
import io.project.edoctor.service.UserDataService;
import io.project.edoctor.service.UserService;
import io.project.edoctor.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class EDoctorIntegrationTests {

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private UserDataService userDataService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDataRepository userDataRepository;


    @BeforeEach
    public void setUp() {
        UserDiagnosis diagnosis = new UserDiagnosis();
        diagnosis.setName("Common cold"); diagnosis.setProbability(0.97);
        List<UserDiagnosis> list = new ArrayList<>();
        list.add(diagnosis);

        UserInterview interview = new UserInterview();
        interview.setUserDiagnoses(list);  interview.setDate(LocalDate.now());

        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("pass");
        user.setInterviews(new ArrayList<>());
        interview.setUser(user);

        UserData userData = new UserData();
        userData.setName("Test Testowy");
        userData.setGender("Male");
        userData.setUserData(user);


        Mockito.when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(user);
        Mockito.when(userDataRepository.findByUserData(user))
                .thenReturn(userData);

    }
    
    @Test
    void whenFindByEmailThenReturnUser() {

        User found = userService.findByEmail("test@mail.com");
        assertThat(found.getEmail())
                .isEqualTo("test@mail.com");

    }

    @Test
    void whenChangeEmailThenDontReturnUser(){

        User found = userService.findByEmail("test@mail.com");
        found.setEmail("tescik@mail.com");

        assertThat(found.getEmail())
                .isNotEqualTo("test@mail.com");

    }

    @Test
    void whenFindByUserThenReturnUserData() {

        User user = userService.findByEmail("test@mail.com");

        UserData found = userDataService.findByUser(user);
        assertThat(found.getUserData())
                .isEqualTo(user);
    }

    @Test
    void whenRegisteredThrowException(){
        Assertions.assertThrows(InvalidEmailOrPassword.class, () -> userService.isRegistered("test@mail.com"));
    }

    @Test
    void whenGetInterviewsThenReturnList(){
        User user = userService.findByEmail("test@mail.com");
        assertThat(user.getInterviews().size()).isEqualTo(1);
    }

    @Test
    void whenGetDiagnosisNameThenReturnName(){
        User user = userService.findByEmail("test@mail.com");
        assertThat(user.getInterviews().get(0).getUserDiagnoses().get(0).getName()).isEqualTo("Common cold");
    }

}
