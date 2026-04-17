package com.Project.TaskManagementApp.Controllers;

import com.Project.TaskManagementApp.DTO.TaskDto;
import com.Project.TaskManagementApp.Entity.ApiResponse;
import com.Project.TaskManagementApp.Entity.Task;
import com.Project.TaskManagementApp.Entity.User;
import com.Project.TaskManagementApp.Repository.TaskRepository;
import com.Project.TaskManagementApp.Services.TaskServices;
import com.Project.TaskManagementApp.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Task")
public class TaskController {
    @Autowired
    private TaskServices taskServices;
    @Autowired
    private UserServices userServices;

    @PostMapping("/new")
    public ResponseEntity<ApiResponse> createTask(@RequestBody Task task) {



        //  VALIDATION
        if (task.getStartDate() != null && task.getEndDate() != null) {
            if (task.getEndDate().before(task.getStartDate())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse("End date cannot be before start date", null));
            }
        }

        Task createdTask = taskServices.createTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(
                        "Task Created Successfully",
                        new TaskDto(
                                createdTask.getTitle(),
                                createdTask.getDescription(),
                                createdTask.getStartDate(),
                                createdTask.getEndDate(),
                                createdTask.getCreatedAt(),
                                createdTask.getStatus()
                        )
                )
        );
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<ApiResponse> getTask(@PathVariable String id) {

        Task task = taskServices.getTask(id);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Task not found", null));
        }

        return ResponseEntity.ok(
                new ApiResponse("Task found", new TaskDto(task.getTitle(), task.getDescription(), task.getStartDate(),task.getEndDate(),task.getCreatedAt(),task.getStatus()))
        );
    }

        @GetMapping("/tasks")
        public ResponseEntity<ApiResponse> getTasks() {
            List<Task> tasks= taskServices.getAllTasks();
            if (tasks == null || tasks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("Tasks not found", tasks));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Tasks found", tasks));

        }

    @PutMapping("/task/{id}")
    public ResponseEntity<ApiResponse> updateTask(@PathVariable String id,
                                                  @RequestBody Task task) {

        // VALIDATION FIRST
        if (task.getStartDate() != null && task.getEndDate() != null) {
            if (task.getEndDate().before(task.getStartDate())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse("End date cannot be before start date", null));
            }
        }

        boolean isUpdated = taskServices.updateTask(task, id);

        if (isUpdated) {
            return ResponseEntity.ok(
                    new ApiResponse("Task Updated Successfully", null)
            );
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse("Task Update Failed", null));
    }
        @DeleteMapping("/task/{id}")
                public ResponseEntity<ApiResponse> deleteTask(@PathVariable String id) {
            boolean isDeleted=taskServices.deleteTask(id);
            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Task Deleted Succesfully",null));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Task Deletion Failed",null));
        }


    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse> getOverdueTasks() {

        List<Task> tasks = taskServices.getOverdueTasks();
        if(tasks==null)
            return ResponseEntity.status(404).body(new ApiResponse("Tasks not found", null));

        return ResponseEntity.ok(
                new ApiResponse("Overdue tasks fetched", tasks)
        );
    }






}
