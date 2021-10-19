package com.eshcherbinina.generalstore.dto;

import com.eshcherbinina.generalstore.dao.entity.Order;
import com.eshcherbinina.generalstore.dao.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {

    private long id;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 5, max = 10, message = "Password should be from 5 to 10 symbols")
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
