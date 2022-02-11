package me.oddlyoko.demo.controllers.project;

import me.oddlyoko.demo.DemoApplication;
import me.oddlyoko.demo.exceptions.ResourceNotFoundException;
import me.oddlyoko.demo.models.Task;
import me.oddlyoko.demo.payloads.responses.PagedResponse;
import me.oddlyoko.demo.payloads.responses.TaskResponse;
import me.oddlyoko.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<TaskResponse>> getAllTasks(
            @RequestParam(name = "page", required = false, defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(name = "size", required = false, defaultValue = DemoApplication.DEFAULT_PAGE_SIZE) @PositiveOrZero int size) {
        Page<TaskResponse> tasks = taskService.getAllTasks(page, size).map(TaskResponse::fromModel);
        PagedResponse<TaskResponse> response = new PagedResponse<>(tasks.getContent(), page, tasks.getNumberOfElements(), tasks.getTotalElements(), tasks.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable("id") long id) throws ResourceNotFoundException {
        Task task = taskService.getTask(id).orElseThrow(() -> new ResourceNotFoundException("Task " + id + " does not exist"));
        return new ResponseEntity<>(TaskResponse.fromModel(task), HttpStatus.OK);
    }
}
