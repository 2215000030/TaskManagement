package com.Project.TaskManagementApp.Entity;


import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document(collection="Task_Entries")
@Data
@NoArgsConstructor
public class Task {
    @Id
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private Date startDate;
    @NonNull
    private Date endDate;
    private Date createdAt;


    private TaskStatus status = TaskStatus.INCOMPLETE;

    private String userId;


}
