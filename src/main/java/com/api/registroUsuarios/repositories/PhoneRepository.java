package com.api.registroUsuarios.repositories;

import com.api.registroUsuarios.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, UUID> {
    @Query("SELECT p FROM Phone p WHERE p.user.id = :userId")
    List<Phone> findByUserId(@Param("userId") UUID userId);
}
