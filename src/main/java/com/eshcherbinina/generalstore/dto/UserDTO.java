package com.eshcherbinina.generalstore.dto;

import com.eshcherbinina.generalstore.dao.entity.Order;
import com.eshcherbinina.generalstore.dao.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
public class UserDTO {

    private long id;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

    private Set<Order> orders;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public User toEntity() {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password).build();
    }
}
