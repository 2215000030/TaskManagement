package com.Project.TaskManagementApp.Controllers;


import com.Project.TaskManagementApp.DTO.UserDto;
import com.Project.TaskManagementApp.Entity.ApiResponse;
import com.Project.TaskManagementApp.Entity.User;
import com.Project.TaskManagementApp.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserServices userServices;





    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        boolean isUpdated= userServices.updateUser(username,user);
        if(isUpdated){
            return ResponseEntity.ok().body(new ApiResponse("User Updated Successfully",null));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("User Not Updated Successfully",null));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        boolean isDeleted= userServices.deleteUser(username);
        if(isDeleted){
            return ResponseEntity.ok().body(new ApiResponse("User Deleted Successfully",null));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("User Not Deleted Successfully",null));

    }



}
