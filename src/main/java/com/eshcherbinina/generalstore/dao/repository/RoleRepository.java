package com.eshcherbinina.generalstore.dao.repository;

import com.eshcherbinina.generalstore.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String role);
}
