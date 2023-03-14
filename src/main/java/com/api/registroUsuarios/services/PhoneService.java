package com.api.registroUsuarios.services;

import com.api.registroUsuarios.entities.Phone;

import java.util.List;
import java.util.UUID;

public interface PhoneService {
    List<Phone> getPhones();
    Phone save(Phone phone);
    List<Phone> getPhonesByUserId(UUID userId);
}
