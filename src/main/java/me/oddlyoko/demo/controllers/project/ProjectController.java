package me.oddlyoko.demo.controllers.project;

import me.oddlyoko.demo.DemoApplication;
import me.oddlyoko.demo.exceptions.ResourceNotFoundException;
import me.oddlyoko.demo.models.Project;
import me.oddlyoko.demo.payloads.requests.project.ProjectRequest;
import me.oddlyoko.demo.payloads.responses.EmptyResponse;
import me.oddlyoko.demo.payloads.responses.PagedResponse;
import me.oddlyoko.demo.payloads.responses.ProjectResponse;
import me.oddlyoko.demo.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ProjectResponse>> getAllProjects(
            @RequestParam(name = "page", required = false, defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(name = "size", required = false, defaultValue = DemoApplication.DEFAULT_PAGE_SIZE) @PositiveOrZero int size) {
        Page<ProjectResponse> projects = projectService.getAllProjects(page, size).map(ProjectResponse::fromModel);
        PagedResponse<ProjectResponse> response = new PagedResponse<>(projects.getContent(), page, projects.getNumberOfElements(), projects.getTotalElements(), projects.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable("id") long id) throws ResourceNotFoundException {
        Project project = projectService.getProject(id).orElseThrow(() -> new ResourceNotFoundException("Project " + id + " does not exist"));
        return new ResponseEntity<>(ProjectResponse.fromModel(project), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> addProject(@Valid @RequestBody ProjectRequest projectRequest) {
        Project project = projectService.addProject(new Project(projectRequest.name(), List.of()));
        return new ResponseEntity<>(ProjectResponse.fromModel(project), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable("id") long id,
            @Valid @RequestBody ProjectRequest projectRequest) throws ResourceNotFoundException {
        Project project = projectService.getProject(id).orElseThrow(() -> new ResourceNotFoundException("Project " + id + " does not exist"));
        project.setName(projectRequest.name());
        project = projectService.updateProject(project);
        return new ResponseEntity<>(ProjectResponse.fromModel(project), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmptyResponse> deleteProject(@PathVariable(name = "id") long id) {
        if (projectService.deleteProject(id))
            return new ResponseEntity<>(new EmptyResponse(), HttpStatus.NO_CONTENT);
        throw new ResourceNotFoundException("Project " + id + " does not exist");
    }
}
