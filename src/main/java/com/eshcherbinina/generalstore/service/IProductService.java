package com.eshcherbinina.generalstore.service;

import com.eshcherbinina.generalstore.dto.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProduct(long id);
    void addNewProduct(ProductDTO product);
}
