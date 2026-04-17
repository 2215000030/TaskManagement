package com.Project.TaskManagementApp.Controllers;


import com.Project.TaskManagementApp.DTO.UserDto;
import com.Project.TaskManagementApp.Entity.ApiResponse;
import com.Project.TaskManagementApp.Entity.User;
import com.Project.TaskManagementApp.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {

       List<User> users = userServices.getAllUsers();
       return ResponseEntity.ok().body(new ApiResponse("Fetched Successfully",users));
    }

    @PostMapping("/new")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        User admin=userServices.createAdmin(user);
        return ResponseEntity.ok().body(new ApiResponse("Admin Created Successfully",new UserDto(admin.getUsername())));
    }
}
