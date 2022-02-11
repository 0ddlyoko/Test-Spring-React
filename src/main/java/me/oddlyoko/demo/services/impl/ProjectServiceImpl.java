package me.oddlyoko.demo.services.impl;

import me.oddlyoko.demo.models.Project;
import me.oddlyoko.demo.repositories.ProjectRepository;
import me.oddlyoko.demo.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Page<Project> getAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return projectRepository.findAll(pageable);
    }

    @Override
    public Optional<Project> getProject(long projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public boolean existProject(long projectId) {
        return projectRepository.existsById(projectId);
    }

    @Override
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public boolean deleteProject(long projectId) {
        return getProject(projectId).map(this::deleteProject).orElse(false);
    }

    @Override
    public boolean deleteProject(Project project) {
        try {
            projectRepository.delete(project);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            // Try to delete a resource but the database returned an empty row
            return false;
        }
    }
}
