package com.api.registroUsuarios.repositories;

import com.api.registroUsuarios.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findOneByEmail(String email);
}
