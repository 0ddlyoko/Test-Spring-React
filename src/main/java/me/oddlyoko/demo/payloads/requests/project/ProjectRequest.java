package me.oddlyoko.demo.payloads.requests.project;

import javax.validation.constraints.NotBlank;

public record ProjectRequest(@NotBlank String name) {

    // Incoming request
    public ProjectRequest() {
        this("");
    }
}
