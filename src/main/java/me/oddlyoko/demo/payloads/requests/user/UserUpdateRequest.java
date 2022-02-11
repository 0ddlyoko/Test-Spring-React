package me.oddlyoko.demo.payloads.requests.user;

import javax.validation.constraints.NotBlank;

public record UserUpdateRequest(@NotBlank String name) {

    // Incoming request
    public UserUpdateRequest() {
        this("");
    }
}
