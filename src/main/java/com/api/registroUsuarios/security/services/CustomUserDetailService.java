package com.api.registroUsuarios.security.services;

import com.api.registroUsuarios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.api.registroUsuarios.entities.User user = userService.getByEmail(username);
        return new User(user.getEmail(),"{noop}"+user.getPassword(),new ArrayList<>());
    }

}
