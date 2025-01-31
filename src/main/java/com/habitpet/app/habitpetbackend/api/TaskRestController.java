package com.habitpet.app.habitpetbackend.api;

import com.habitpet.app.habitpetbackend.application.TaskService;
import com.habitpet.app.habitpetbackend.application.dto.TaskInDTO;
import com.habitpet.app.habitpetbackend.application.dto.TaskOutDTO;
import com.habitpet.app.habitpetbackend.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskRestController {

    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskOutDTO> createTask(@RequestBody TaskInDTO taskDTO, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.createTaskForUser(taskDTO, taskService.findUserById(user.getId())));
    }

    @GetMapping("/user")
    public ResponseEntity<List<TaskOutDTO>> getTasks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getTasksForUser(taskService.findUserById(user.getId())));
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskOutDTO> patchNameAndTypeAndStatus(@PathVariable String taskId, @RequestBody TaskInDTO taskInDTO, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(taskService.patchNameAndTypeAndStatus(taskId, taskInDTO, taskService.findUserById(user.getId())));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable String taskId, @AuthenticationPrincipal User user) {
        taskService.deleteTask(taskId, taskService.findUserById(user.getId()));
        return ResponseEntity.noContent().build();
    }
}
