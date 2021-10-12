package com.eshcherbinina.generalstore.restController;

import com.eshcherbinina.generalstore.dto.ResetPassword;
import com.eshcherbinina.generalstore.dto.ResetPasswordRequest;
import com.eshcherbinina.generalstore.dto.UserDTO;
import com.eshcherbinina.generalstore.responses.ResponseDetails;
import com.eshcherbinina.generalstore.service.IUserService;
import com.eshcherbinina.generalstore.utils.OperationStatus;
import com.eshcherbinina.generalstore.utils.Operations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@RestController
public class UserController {

    private IUserService userService;
    private MessageSource messageSource;

    @Autowired
    public UserController(IUserService userService,
                          MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signUp(@Valid @RequestBody UserDTO userDTO) {
        userService.addNewUser(userDTO);
        return new ResponseEntity<>(messageSource.getMessage("api.response.user.creation.successful",
                null, Locale.ENGLISH), HttpStatus.OK);
    }

    @RequestMapping(value = "/reset-password-request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDetails> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPassword) {
        userService.resetPasswordRequest(resetPassword.getEmail());
        ResponseDetails response = new ResponseDetails();
        response.setName(Operations.RESET_PASSWORD_REQUEST.name());
        response.setResult(OperationStatus.SUCCESS.name());
        response.setDetails(messageSource.getMessage("api.response.user.reset.password.request.successful",
                    null, Locale.ENGLISH));
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDetails> saveNewPassword(@Valid @RequestBody ResetPassword resetPassword) {

        ResponseDetails response = new ResponseDetails();
        response.setName(Operations.RESET_PASSWORD.name());
        response.setResult(OperationStatus.SUCCESS.name());
        userService.resetPassword(resetPassword);
        response.setDetails(messageSource.getMessage("api.response.user.reset.password.successful",
                null, Locale.ENGLISH));
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
