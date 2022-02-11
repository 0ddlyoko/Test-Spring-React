package me.oddlyoko.demo.payloads.responses;

import me.oddlyoko.demo.models.User;

public record UserResponse(long id, String email, String name) {

    public static UserResponse fromModel(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getName());
    }
}
