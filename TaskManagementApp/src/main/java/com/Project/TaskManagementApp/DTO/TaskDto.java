package com.Project.TaskManagementApp.DTO;

import com.Project.TaskManagementApp.Entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String title;

    private String description;

    private Date startDate;

    private Date endDate;
    private Date createdAt;
    private TaskStatus taskStatus;
}
