package me.oddlyoko.demo.services;

import me.oddlyoko.demo.models.Project;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProjectService {

    Page<Project> getAllProjects(int page, int size);

    Optional<Project> getProject(long projectId);

    boolean existProject(long projectId);

    Project addProject(Project project);

    Project updateProject(Project project);

    boolean deleteProject(long projectId);

    boolean deleteProject(Project project);
}
