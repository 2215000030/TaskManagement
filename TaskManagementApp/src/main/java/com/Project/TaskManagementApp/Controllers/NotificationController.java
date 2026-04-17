package com.Project.TaskManagementApp.Controllers;


import com.Project.TaskManagementApp.DTO.NotificationDto;
import com.Project.TaskManagementApp.Entity.ApiResponse;
import com.Project.TaskManagementApp.Entity.Notification;
import com.Project.TaskManagementApp.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllNotifications() {
        List<Notification> notifications=notificationService.getUnreadNotifications();
        if(notifications==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Notifications present",null));

        }
        return ResponseEntity.status(HttpStatus.OK).body(new NotificationDto(notifications.size(),notifications));

    }

//    @PutMapping("/read/{id}")
//    public ResponseEntity<?> readNotification(@PathVariable String id) {
//        boolean isRead=notificationService.markAsRead(id);
//        if(isRead){
//            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Notification Read",null));
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Notification Not Found",null));
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable String id) {
         boolean isDeleted=notificationService.deleteNotification(id);
         if(isDeleted){
             return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Notification Deleted",null));

         }
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Notification Not Found",null));
    }


}
