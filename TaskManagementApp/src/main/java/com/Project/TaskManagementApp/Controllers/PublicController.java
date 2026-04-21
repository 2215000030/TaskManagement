package com.Project.TaskManagementApp.Controllers;

import com.Project.TaskManagementApp.DTO.UserDto;
import com.Project.TaskManagementApp.Entity.ApiResponse;
import com.Project.TaskManagementApp.Entity.User;
import com.Project.TaskManagementApp.Services.UserServices;
import com.Project.TaskManagementApp.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Public")
public class PublicController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {

        User createduser=userServices.createUser(user);
        return ResponseEntity.ok().body(new ApiResponse("User Created Successfully",new UserDto(createduser.getUsername())));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
            String s = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Logged in successfully", s));

        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("Invalid Credentials", e.getMessage()));
        }
    }
}
