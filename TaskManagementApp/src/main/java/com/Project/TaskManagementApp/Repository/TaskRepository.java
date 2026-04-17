package com.Project.TaskManagementApp.Repository;

import com.Project.TaskManagementApp.Entity.Task;
import com.Project.TaskManagementApp.Entity.TaskStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUserId(String userId);

    List<Task> findByStatus(TaskStatus taskStatus);
    List<Task> findByUserIdAndStatusNot(String userId, TaskStatus status);
}
