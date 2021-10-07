package com.eshcherbinina.generalstore.dao.repositiry;

import com.eshcherbinina.generalstore.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
}
