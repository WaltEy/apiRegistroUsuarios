package com.api.registroUsuarios.services.impl;

import com.api.registroUsuarios.entities.Phone;
import com.api.registroUsuarios.repositories.PhoneRepository;
import com.api.registroUsuarios.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public List<Phone> getPhones() {
        return phoneRepository.findAll();
    }

    @Override
    public Phone save(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Override
    public List<Phone> getPhonesByUserId(UUID userId) {
        return phoneRepository.findByUserId(userId);
    }
}
