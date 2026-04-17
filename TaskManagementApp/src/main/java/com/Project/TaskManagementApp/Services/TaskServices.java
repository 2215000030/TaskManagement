package com.Project.TaskManagementApp.Services;

import com.Project.TaskManagementApp.Entity.Task;
import com.Project.TaskManagementApp.Entity.TaskStatus;
import com.Project.TaskManagementApp.Entity.User;
import com.Project.TaskManagementApp.Repository.TaskRepository;
import com.Project.TaskManagementApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServices {


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Task createTask(Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        task.setCreatedAt(new Date());
        task.setUserId(user.getId());
        Task createdTask = taskRepository.save(task);
        return createdTask;
    }

    public Task getTask(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        Task task = taskRepository.findById(id).orElse(null);
        if(task==null || !task.getUserId().equals(user.getId())) {
            return null;
        }
        return task;

    }

    public List<Task> getAllTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null)
            return null;
       return taskRepository.findByUserId(user.getId());
    }

    @Transactional
    public boolean updateTask(Task task, String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null){
            System.out.println("User not found");
            return false;}

        Task existingTask = taskRepository.findById(id).orElse(null);


        if (existingTask == null   || !existingTask.getUserId().equals(user.getId())){
            System.out.println("Task not found");
            return false;}

        if (task.getDescription() != null && !task.getDescription().isEmpty()) {
            existingTask.setDescription(task.getDescription());
        }
        if (task.getTitle() != null && !task.getTitle().isEmpty()) {
            existingTask.setTitle(task.getTitle());
        }
        if (task.getEndDate() != null) {
            existingTask.setEndDate(task.getEndDate());
        }
        if (task.getStartDate() != null) {
            existingTask.setStartDate((task.getStartDate()));
        }
        if(task.getStatus()!=null){
            existingTask.setStatus(task.getStatus());
        }
        taskRepository.save(existingTask);

        return true;
    }

    @Transactional
    public boolean deleteTask(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return false;
        Task existingTask = taskRepository.findById(id).orElse(null);

        if (existingTask == null || !existingTask.getUserId().equals(user.getId())) return false;
        taskRepository.delete(existingTask);
        return true;
    }

    public List<Task> getOverdueTasks() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return null;

        Date today = startOfToday();

        List<Task> tasks =
                taskRepository.findByUserIdAndStatusNot(user.getId(), TaskStatus.COMPLETED);

        return tasks.stream()
                .filter(task ->
                        task.getEndDate() != null &&
                                task.getEndDate().before(today)   // 🔥 FIX IS HERE
                )
                .toList();
    }


    private Date startOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
