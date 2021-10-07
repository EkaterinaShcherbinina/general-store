package com.eshcherbinina.generalstore.validation;

import com.eshcherbinina.generalstore.dto.UserDTO;
import com.eshcherbinina.generalstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserValidator implements Validator {

    private UserService userService;
    private final String userExistsMessage = "User with this email already exists";

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDto = (UserDTO) target;
        if(userService.isUserExists(userDto.getEmail())) {
            //errors.rejectValue("email", "409", userExistsMessage);
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, userExistsMessage);
        }
    }
}
