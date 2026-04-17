package com.Project.TaskManagementApp.Controllers;


import com.Project.TaskManagementApp.Entity.User;
import com.Project.TaskManagementApp.Services.DogApiService;
import com.Project.TaskManagementApp.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/Api")
public class DogApiController {

    @Autowired
    private DogApiService dogApiService;
    @Autowired
    private RestTemplate restTemplate;


    @GetMapping
    public String getDogApi(){
        return dogApiService.getDogApi();
    }

    @PostMapping
    public boolean createUser(){
        User user = new User();
        user.setUsername("lafda");
        user.setPassword("123");
       return dogApiService.createUser(user);

    }
}
