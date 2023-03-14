package com.api.registroUsuarios.services.impl;

import com.api.registroUsuarios.entities.Phone;
import com.api.registroUsuarios.entities.User;
import com.api.registroUsuarios.entities.dto.UserRequestDTO;
import com.api.registroUsuarios.entities.dto.UserResponseDTO;
import com.api.registroUsuarios.repositories.UserRepository;
import com.api.registroUsuarios.security.utils.JWTUtil;
import com.api.registroUsuarios.services.PhoneService;
import com.api.registroUsuarios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private JWTUtil jwtUtil;

    public User getUser(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    //crea un usuario nuevo, que es guardado en la base de datos
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {

        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setToken(jwtUtil.generateToken(userRequestDTO));
        user.setCreated(new Date());
        user.setLast_login(new Date());
        user.setModified(new Date());
        user.setIsactive(true);
        User userSaved = userRepository.save(user);

        List<Phone> phones = userRequestDTO.getPhones();
        phones.forEach(phone -> {
            phone.setUser(userSaved);
            phoneService.save(phone);}
        );

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userSaved.getId());
        userResponseDTO.setToken(jwtUtil.generateToken(userRequestDTO)); //generación del Token
        userResponseDTO.setCreated(new Date());
        userResponseDTO.setLast_login(new Date());
        userResponseDTO.setModified(new Date());
        userResponseDTO.setIsactive(true);

        return userResponseDTO;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }


}
