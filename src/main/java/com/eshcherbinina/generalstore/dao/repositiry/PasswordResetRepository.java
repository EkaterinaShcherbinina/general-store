package com.eshcherbinina.generalstore.dao.repositiry;

import com.eshcherbinina.generalstore.dao.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
