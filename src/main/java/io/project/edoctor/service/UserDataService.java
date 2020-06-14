package io.project.edoctor.service;

import io.project.edoctor.model.entity.User;
import io.project.edoctor.model.entity.UserData;
import io.project.edoctor.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {

    @Autowired
    private UserDataRepository userDataRepository;


    public void saveUserData(UserData userData){
        userDataRepository.save(userData);

    }

    public UserData findByUser(User user){
        return userDataRepository.findByUserData(user);
    }
}
