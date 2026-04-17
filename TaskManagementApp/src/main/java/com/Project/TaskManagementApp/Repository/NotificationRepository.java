package com.Project.TaskManagementApp.Repository;

import com.Project.TaskManagementApp.Entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByUserIdAndIsReadFalse(String userId);

    List<Notification> findByUserId(String userId);



    boolean existsByTaskIdAndUserIdAndIsReadFalse(String id, String userId);
}
