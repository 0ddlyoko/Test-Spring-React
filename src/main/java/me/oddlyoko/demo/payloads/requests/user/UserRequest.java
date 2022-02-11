package me.oddlyoko.demo.payloads.requests.user;

import javax.validation.constraints.NotBlank;

public record UserRequest(@NotBlank String email, @NotBlank String name) {

    // Incoming request
    public UserRequest() {
        this("", "");
    }
}
