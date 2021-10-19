package com.eshcherbinina.generalstore.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ResetPassword {
    @NotEmpty
    private String token;

    @NotEmpty
    @Size(min = 5, max = 10, message = "Password should be from 5 to 10 symbols")
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
