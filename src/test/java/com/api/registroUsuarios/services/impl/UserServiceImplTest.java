package com.api.registroUsuarios.services.impl;

import com.api.registroUsuarios.entities.Phone;
import com.api.registroUsuarios.entities.User;
import com.api.registroUsuarios.entities.dto.UserRequestDTO;
import com.api.registroUsuarios.entities.dto.UserResponseDTO;
import com.api.registroUsuarios.repositories.UserRepository;
import com.api.registroUsuarios.security.utils.JWTUtil;
import com.api.registroUsuarios.services.PhoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhoneService phoneService;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setName("John");
        user.setEmail("john@example.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getUser(id);

        assertEquals(user, result);
    }

    @Test
    public void testGetUsers() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setName("Walter");
        user1.setEmail("test@ejemplo.com");
        userList.add(user1);

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setName("Florencia");
        user2.setEmail("test2@ejemplo.com");
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getUsers();

        assertEquals(userList, result);

    }

    @Test
    public void testSaveUser() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("WalterTest");
        userRequestDTO.setEmail("test@ejemplo.com");
        userRequestDTO.setPassword("password");

        Phone phone = new Phone();
        phone.setNumber("1234567890");

        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(phone);

        userRequestDTO.setPhones(phoneList);

        when(jwtUtil.generateToken(userRequestDTO)).thenReturn("token");

        when(userRepository.save(any(User.class))).thenReturn(new User());

        UserResponseDTO result = userService.saveUser(userRequestDTO);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setIsactive(true);

        assertEquals("token", result.getToken());
        assertEquals(userResponseDTO.getIsactive(), result.getIsactive());
    }

    @Test
    public void testGetByEmail() {
        String email = "test@ejemplo.cl";
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Walter");
        user.setEmail(email);

        when(userRepository.findOneByEmail(email)).thenReturn(user);

        User result = userService.getByEmail(email);

        assertEquals(user, result);
    }
}