package com.api.registroUsuarios.controllers;

import com.api.registroUsuarios.entities.User;
import com.api.registroUsuarios.entities.dto.LoginResponse;
import com.api.registroUsuarios.entities.dto.UserLoginDTO;
import com.api.registroUsuarios.entities.dto.UserRequestDTO;
import com.api.registroUsuarios.entities.dto.UserResponseDTO;
import com.api.registroUsuarios.exceptions.EmailFormatException;
import com.api.registroUsuarios.exceptions.FindedEmailException;
import com.api.registroUsuarios.exceptions.PasswordFormatException;
import com.api.registroUsuarios.security.services.CustomUserDetailService;
import com.api.registroUsuarios.security.utils.JWTUtil;
import com.api.registroUsuarios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JWTUtil jwtUtil;

    @Value("${regex.validation.email}")
    private String emailValidation;

    @Value("${regex.validation.password}")
    private String passwordValidation;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/logIn")
    public ResponseEntity<LoginResponse> logInUser(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(),userLoginDTO.getPassword()));
            UserDetails userDetails = customUserDetailService.loadUserByUsername(userLoginDTO.getEmail());
            String jwt = jwtUtil.generateToken(userDetails);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwt);

            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    //Endpoint para el registro de usuarios
    @PostMapping("/signUp")
    public ResponseEntity<UserResponseDTO> signUpUser(@RequestBody UserRequestDTO userRequestDTO) throws FindedEmailException, PasswordFormatException, EmailFormatException {
        User userFindedEmail = userService.getByEmail(userRequestDTO.getEmail());

        //valida si el mail ya existe en la base de datos.
        if (userFindedEmail != null){
            throw new FindedEmailException("El email " + userRequestDTO.getEmail() + " ya existe.");
        }

        ////valida si la contraseña respeta el formato correcto
        Boolean correctPassword = Pattern.matches(passwordValidation,userRequestDTO.getPassword());
        if (!correctPassword){
            throw new PasswordFormatException("La contraseña debe tener una mayuscula, letras minúsculas, y dos numeros ");
        }

        //valida si el mail respeta el formato correcto
        Boolean correctEmail = Pattern.matches(emailValidation,userRequestDTO.getEmail());
        if (!correctEmail){
            throw new EmailFormatException("El Emai debe tener el formato aaaaaaa@dominio.cl");
        }

        //devuelve La informacion
        return new ResponseEntity<>(userService.saveUser(userRequestDTO), HttpStatus.CREATED);
    }
}
