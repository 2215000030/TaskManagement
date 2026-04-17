package com.Project.TaskManagementApp.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    private String id;

    private String message;

    private String userId;

    private String taskId;

    private boolean isRead = false;

    private Date createdAt = new Date();
}
