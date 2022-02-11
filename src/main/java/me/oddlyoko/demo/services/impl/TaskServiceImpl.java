package me.oddlyoko.demo.services.impl;

import me.oddlyoko.demo.models.Task;
import me.oddlyoko.demo.repositories.TaskRepository;
import me.oddlyoko.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Page<Task> getAllTasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return taskRepository.findAll(pageable);
    }

    @Override
    public Page<Task> getAllTasks(long projectId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return taskRepository.findAllByProjectId(projectId, pageable);
    }

    @Override
    public Optional<Task> getTask(long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public boolean deleteTask(long taskId) {
        return getTask(taskId).map(this::deleteTask).orElse(false);
    }

    @Override
    public boolean deleteTask(Task task) {
        try {
            taskRepository.delete(task);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            // Try to delete a resource but the database returned an empty row
            return false;
        }
    }
}
