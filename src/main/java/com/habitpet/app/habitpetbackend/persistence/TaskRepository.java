package com.habitpet.app.habitpetbackend.persistence;

import com.habitpet.app.habitpetbackend.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByUserId(String userId);
}
