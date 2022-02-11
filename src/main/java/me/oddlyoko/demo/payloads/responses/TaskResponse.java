package me.oddlyoko.demo.payloads.responses;

import me.oddlyoko.demo.models.Task;

public record TaskResponse(long id, String name, long projectId) {

    public static TaskResponse fromModel(Task task) {
        return new TaskResponse(task.getId(), task.getName(), task.getProject().getId());
    }
}
