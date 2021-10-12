package com.eshcherbinina.generalstore.service;

import com.eshcherbinina.generalstore.dto.ResetPassword;
import com.eshcherbinina.generalstore.dto.UserDTO;

public interface IUserService {
    void addNewUser(UserDTO user);
    void resetPasswordRequest(String email);
    void resetPassword(ResetPassword password);
}
