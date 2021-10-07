package com.eshcherbinina.generalstore.service;

import com.eshcherbinina.generalstore.dao.entity.Role;
import com.eshcherbinina.generalstore.dao.entity.User;
import com.eshcherbinina.generalstore.dao.repositiry.RoleRepository;
import com.eshcherbinina.generalstore.dao.repositiry.UserRepository;
import com.eshcherbinina.generalstore.dto.UserDTO;
import com.eshcherbinina.generalstore.utils.ExistingRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addNewUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toEntity();
        Role role = roleRepository.findByName(ExistingRoles.USER.toString());
        user.addRole(role);
        userRepository.save(user);
    }

    @Override
    public boolean isUserExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
