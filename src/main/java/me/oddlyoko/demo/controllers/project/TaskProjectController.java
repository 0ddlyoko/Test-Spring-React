package me.oddlyoko.demo.controllers.project;

import me.oddlyoko.demo.DemoApplication;
import me.oddlyoko.demo.exceptions.ResourceNotFoundException;
import me.oddlyoko.demo.models.Project;
import me.oddlyoko.demo.models.Task;
import me.oddlyoko.demo.payloads.requests.project.TaskRequest;
import me.oddlyoko.demo.payloads.responses.EmptyResponse;
import me.oddlyoko.demo.payloads.responses.PagedResponse;
import me.oddlyoko.demo.payloads.responses.TaskResponse;
import me.oddlyoko.demo.services.ProjectService;
import me.oddlyoko.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/api/project/{projectId}/task")
public class TaskProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;

    @Autowired
    public TaskProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<TaskResponse>> getAllTasks(
            @PathVariable long projectId,
            @RequestParam(name = "page", required = false, defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(name = "size", required = false, defaultValue = DemoApplication.DEFAULT_PAGE_SIZE) @PositiveOrZero int size) {
        if (!projectService.existProject(projectId))
            throw new ResourceNotFoundException("Project " + projectId + " does not exist");
        Page<TaskResponse> tasks = taskService.getAllTasks(projectId, page, size).map(TaskResponse::fromModel);
        PagedResponse<TaskResponse> response = new PagedResponse<>(tasks.getContent(), page, tasks.getNumberOfElements(), tasks.getTotalElements(), tasks.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable long projectId, @PathVariable("id") long id) throws ResourceNotFoundException {
        Task task = taskService.getTask(id).orElseThrow(() -> new ResourceNotFoundException("Task " + id + " does not exist"));
        if (task.getProject().getId() != projectId)
            throw new ResourceNotFoundException("Task " + id + " does not exist for project " + projectId);
        return new ResponseEntity<>(TaskResponse.fromModel(task), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> addTask(@PathVariable long projectId, @Valid @RequestBody TaskRequest taskRequest) {
        Project project = projectService.getProject(projectId).orElseThrow(() -> new ResourceNotFoundException("Project " + projectId + " does not exist"));
        Task task = taskService.addTask(new Task(taskRequest.name(), project));
        return new ResponseEntity<>(TaskResponse.fromModel(task), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable long projectId,
            @PathVariable("id") long id,
            @Valid @RequestBody TaskRequest taskRequest) throws ResourceNotFoundException {
        Task task = taskService.getTask(id).orElseThrow(() -> new ResourceNotFoundException("Task " + id + " does not exist"));
        if (task.getProject().getId() != projectId)
            throw new ResourceNotFoundException("Task " + id + " does not exist for project " + projectId);
        task.setName(taskRequest.name());
        task = taskService.updateTask(task);
        return new ResponseEntity<>(TaskResponse.fromModel(task), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmptyResponse> deleteTask(@PathVariable long projectId, @PathVariable(name = "id") long id) {
        Task task = taskService.getTask(id).orElseThrow(() -> new ResourceNotFoundException("Task " + id + " does not exist"));
        if (task.getProject().getId() != projectId)
            throw new ResourceNotFoundException("Task " + id + " does not exist for project " + projectId);
        if (taskService.deleteTask(task))
            return new ResponseEntity<>(new EmptyResponse(), HttpStatus.NO_CONTENT);
        throw new IllegalStateException("Error while deleting task " + id);
    }
}
