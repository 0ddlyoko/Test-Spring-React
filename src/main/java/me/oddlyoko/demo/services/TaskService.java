package me.oddlyoko.demo.services;

import me.oddlyoko.demo.models.Task;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TaskService {

    Page<Task> getAllTasks(int page, int size);

    Page<Task> getAllTasks(long projectId, int page, int size);

    Optional<Task> getTask(long taskId);

    Task addTask(Task task);

    Task updateTask(Task task);

    boolean deleteTask(long taskId);

    boolean deleteTask(Task task);
}
