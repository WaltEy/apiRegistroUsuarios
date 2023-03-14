package com.api.registroUsuarios.services;

import com.api.registroUsuarios.entities.User;
import com.api.registroUsuarios.entities.dto.UserRequestDTO;
import com.api.registroUsuarios.entities.dto.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUser(UUID id);
    List<User> getUsers();
    UserResponseDTO saveUser(UserRequestDTO user);

    User getByEmail(String email);
}
