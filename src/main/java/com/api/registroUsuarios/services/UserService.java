package com.api.registroUsuarios.services;

import com.api.registroUsuarios.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUser(UUID id);
    List<User> getUsers();
    User saveUser(User user);
    void deleteUser(UUID id);
}
