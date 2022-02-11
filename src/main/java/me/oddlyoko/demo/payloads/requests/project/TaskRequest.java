package me.oddlyoko.demo.payloads.requests.project;

import javax.validation.constraints.NotBlank;

public record TaskRequest(@NotBlank String name) {

    // Incoming request
    public TaskRequest() {
        this("");
    }
}
