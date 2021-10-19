package com.eshcherbinina.generalstore.service;

import com.eshcherbinina.generalstore.dao.entity.PasswordResetToken;
import com.eshcherbinina.generalstore.dao.entity.Role;
import com.eshcherbinina.generalstore.dao.entity.User;
import com.eshcherbinina.generalstore.dao.repository.PasswordResetRepository;
import com.eshcherbinina.generalstore.dao.repository.RoleRepository;
import com.eshcherbinina.generalstore.dao.repository.UserRepository;
import com.eshcherbinina.generalstore.dto.ResetPassword;
import com.eshcherbinina.generalstore.dto.UserDTO;
import com.eshcherbinina.generalstore.exception.ErrorType;
import com.eshcherbinina.generalstore.exception.ExceptionCreator;
import com.eshcherbinina.generalstore.utils.AmazonSes;
import com.eshcherbinina.generalstore.utils.ExistingRoles;
import com.eshcherbinina.generalstore.utils.Utils;
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
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, MessageSource messageSource,
                       PasswordResetRepository passwordResetRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
        this.passwordResetRepository = passwordResetRepository;
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

    @Override
    public void resetPasswordRequest(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) ExceptionCreator.throwException(ErrorType.ENTITY_NOT_FOUND,
                "api.error.user.not.found", "api.error.reset.password.failed", null);

        String token = Utils.generatePasswordResetToken(email);
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetRepository.save(passwordResetToken);

        new AmazonSes().sendPasswordResetRequest(user.getEmail(), token);
    }

    @Override
    public void resetPassword(ResetPassword password) {
        if(Utils.hasTokenExpired(password.getToken()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    messageSource.getMessage("api.error.reset.password.token.expired", null, Locale.ENGLISH));

        PasswordResetToken passwordResetToken = passwordResetRepository.findByToken(password.getToken());
        if(passwordResetToken == null)
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    messageSource.getMessage("api.error.reset.password.token.not.found", null, Locale.ENGLISH));

        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(password.getPassword()));
        userRepository.save(user);

        passwordResetRepository.delete(passwordResetToken);
        return;
    }

    private boolean isUserExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
