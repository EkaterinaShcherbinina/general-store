package com.eshcherbinina.generalstore.restController;

import com.eshcherbinina.generalstore.dto.UserDTO;
import com.eshcherbinina.generalstore.service.IUserService;
import com.eshcherbinina.generalstore.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@RestController
public class AuthorizationController {

    private IUserService userService;
    private UserValidator userValidator;
    private MessageSource messageSource;

    @Autowired
    public AuthorizationController(IUserService userService, UserValidator userValidator,
                                   MessageSource messageSource) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.messageSource = messageSource;
    }

    @InitBinder("userDTO")
    public void initMerchantOnlyBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signUp(@Valid @RequestBody UserDTO userDTO) {
        userService.addNewUser(userDTO);
        return new ResponseEntity<>(messageSource.getMessage("api.response.user.creation.successful",
                null, Locale.ENGLISH), HttpStatus.OK);
    }
}
