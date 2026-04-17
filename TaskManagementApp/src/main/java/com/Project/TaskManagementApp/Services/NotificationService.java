package com.Project.TaskManagementApp.Services;

import com.Project.TaskManagementApp.Entity.Notification;
import com.Project.TaskManagementApp.Entity.Task;
import com.Project.TaskManagementApp.Entity.TaskStatus;
import com.Project.TaskManagementApp.Entity.User;
import com.Project.TaskManagementApp.Repository.NotificationRepository;
import com.Project.TaskManagementApp.Repository.TaskRepository;
import com.Project.TaskManagementApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    //  Run every day at 9 AM
//    "0 * * * * ?"  every minute
//    "0 0 0 * * ?"
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Kolkata")
    public void checkTaskDeadlines() {

        List<Task> tasks = taskRepository.findByStatus(TaskStatus.INCOMPLETE);

        Date now = new Date();

        for (Task task : tasks) {

            long diff = task.getEndDate().getTime() - now.getTime();
            long daysLeft = TimeUnit.MILLISECONDS.toDays(diff);

            // 🛑 Skip past tasks (optional)
            if (daysLeft < 0) continue;

            boolean alreadyExists =
                    notificationRepository.existsByTaskIdAndUserIdAndIsReadFalse(
                            task.getId(),
                            task.getUserId()
                    );

            if (alreadyExists) continue;

            String message;

            if (daysLeft == 0) {
                message = "Task '" + task.getTitle() + "' is due today!";
            } else if (daysLeft == 1) {
                message = "Task '" + task.getTitle() + "' is still pending (1 day left)";
            } else {
                message = "Task '" + task.getTitle() + "' is still pending (" + daysLeft + " days left)";
            }

            Notification notification = new Notification();

            notification.setMessage(message);
            notification.setTaskId(task.getId());
            notification.setUserId(task.getUserId());
            notification.setRead(false);
            notification.setCreatedAt(new Date());

            notificationRepository.save(notification);
        }
    }





    public List<Notification> getUnreadNotifications() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return null;
        return notificationRepository.findByUserIdAndIsReadFalse(user.getId());
    }


    public boolean markAsRead(String notificationId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElse(null);
        if (n == null) return false;
        n.setRead(true);
        notificationRepository.save(n);
        return true;
    }
    public boolean deleteNotification(String notificationId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElse(null);
        if (n == null) return false;
        notificationRepository.delete(n);
        return true;
    }
}