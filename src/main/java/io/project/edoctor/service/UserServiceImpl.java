package io.project.edoctor.service;

import io.project.edoctor.exception.InvalidEmailOrPassword;
import io.project.edoctor.model.entity.User;
import io.project.edoctor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);

        if (user == null){
            throw new InvalidEmailOrPassword("Invalid email or password.");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);

    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void isRegistered(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new InvalidEmailOrPassword("Somebody with that email is already registered.");
        }
    }

    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_PATIENT");
        userRepository.save(user);
    }

}
