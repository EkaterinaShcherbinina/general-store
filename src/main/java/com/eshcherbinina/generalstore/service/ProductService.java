package com.eshcherbinina.generalstore.service;

import com.eshcherbinina.generalstore.dao.entity.Product;
import com.eshcherbinina.generalstore.dao.repository.ProductRepository;
import com.eshcherbinina.generalstore.dto.ProductDTO;
import com.eshcherbinina.generalstore.exception.ErrorType;
import com.eshcherbinina.generalstore.exception.ExceptionCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService{

    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(item -> new ProductDTO(item)).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(long id) {
        Product product = productRepository.findById(id);
        if(product == null) ExceptionCreator.throwException(ErrorType.ENTITY_NOT_FOUND,
                "api.error.product.not.found.message", "api.error.product.not.found.detail", null);
        return new ProductDTO(product);
    }

    @Override
    public void addNewProduct(ProductDTO product) {
        productRepository.save(product.toEntity());
    }
}
