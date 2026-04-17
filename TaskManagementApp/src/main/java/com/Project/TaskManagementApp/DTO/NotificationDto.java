package com.Project.TaskManagementApp.DTO;


import com.Project.TaskManagementApp.Entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private int count;
    private List<Notification> notifications;
}
