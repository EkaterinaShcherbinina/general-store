package com.eshcherbinina.generalstore.service;

import com.eshcherbinina.generalstore.dto.UserDTO;

public interface IUserService {
    void addNewUser(UserDTO user);
    boolean isUserExists(String email);
}
