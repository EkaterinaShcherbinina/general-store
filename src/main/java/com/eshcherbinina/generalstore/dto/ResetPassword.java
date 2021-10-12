package com.eshcherbinina.generalstore.dto;

import javax.validation.constraints.NotEmpty;

public class ResetPassword {
    @NotEmpty
    private String token;
    @NotEmpty
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
