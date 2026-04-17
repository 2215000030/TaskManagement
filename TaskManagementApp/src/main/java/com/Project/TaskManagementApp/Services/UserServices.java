package com.Project.TaskManagementApp.Services;

import com.Project.TaskManagementApp.Entity.User;
import com.Project.TaskManagementApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.getRoles().add("ROLE_USER");
        return userRepository.save(user);

    }

    public User createAdmin(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.getRoles().add("ROLE_ADMIN");
       return userRepository.save(user);

    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }
    public boolean updateUser(String username, User newUser)
    {
        User user=userRepository.findByUsername(username).orElse(null);
        if(user==null)
            return false;
        String newusername=newUser.getUsername();
        if(newusername!=null && !newusername.equals(""))
        user.setUsername(newUser.getUsername());
        String newpassword=newUser.getPassword();
        if(newpassword!=null && !newpassword.equals(""))
        user.setPassword(encoder.encode(newUser.getPassword()));
        userRepository.save(user);
        return true;
    }
    public boolean deleteUser(String username) {
        User user=userRepository.findById(username).orElse(null);
        if(user==null)
            return false;
        userRepository.deleteByUsername(username);
        return true;
    }




}
