package com.eshcherbinina.generalstore.service;

import com.eshcherbinina.generalstore.dao.entity.Role;
import com.eshcherbinina.generalstore.dao.entity.User;
import com.eshcherbinina.generalstore.dao.repositiry.RoleRepository;
import com.eshcherbinina.generalstore.dao.repositiry.UserRepository;
import com.eshcherbinina.generalstore.dto.UserDTO;
import com.eshcherbinina.generalstore.utils.ExistingRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Service
public class UserService implements IUserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private MessageSource messageSource;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    @Override
    public void addNewUser(UserDTO userDTO) {
        if(isUserExists(userDTO.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    messageSource.getMessage("api.error.user.already.registered", null, Locale.ENGLISH));
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toEntity();
        Role role = roleRepository.findByName(ExistingRoles.USER.toString());
        user.addRole(role);
        userRepository.save(user);
    }

    private boolean isUserExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
