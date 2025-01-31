package com.habitpet.app.habitpetbackend.application;

import com.habitpet.app.habitpetbackend.application.dto.TaskDTO;
import com.habitpet.app.habitpetbackend.application.dto.TaskInDTO;
import com.habitpet.app.habitpetbackend.application.dto.TaskOutDTO;
import com.habitpet.app.habitpetbackend.domain.Task;
import com.habitpet.app.habitpetbackend.domain.User;
import com.habitpet.app.habitpetbackend.persistence.TaskRepository;
import com.habitpet.app.habitpetbackend.persistence.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TaskOutDTO createTaskForUser(TaskInDTO taskDTO, User user) {
        Task task = new Task();
        task.setName(taskDTO.name());
        task.setEstimatedTime(taskDTO.estimatedTime());
        task.setType(taskDTO.type());
        task.setStatus(taskDTO.status());
        task.setUser(user);
        taskRepository.save(task);
        return new TaskOutDTO(task);
    }

    public List<TaskOutDTO> getTasksForUser(User user) {
        return taskRepository.findByUserId(user.getId())
                .stream()
                .map(TaskOutDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTask(String taskId, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("No tienes permiso para eliminar esta tarea.");
        }

        taskRepository.delete(task);
    }

    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public TaskOutDTO patchNameAndTypeAndStatus(String taskId, TaskInDTO taskInDTO, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("No tienes permiso para modificar esta tarea.");
        }
        task.setName(taskInDTO.name());
        task.setType(taskInDTO.type());
        task.setStatus(taskInDTO.status());
        taskRepository.save(task);
        return new TaskOutDTO(task);
    }
}
