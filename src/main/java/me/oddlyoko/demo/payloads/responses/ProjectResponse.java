package me.oddlyoko.demo.payloads.responses;

import me.oddlyoko.demo.models.Project;
import me.oddlyoko.demo.models.Task;

import java.util.List;

public record ProjectResponse(long id, String name, List<Long> tasks) {

    public static ProjectResponse fromModel(Project project) {
        return new ProjectResponse(project.getId(), project.getName(), project.getTasks().stream().map(Task::getId).toList());
    }
}
