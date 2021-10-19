package com.eshcherbinina.generalstore.unit;

import com.eshcherbinina.generalstore.dao.entity.Product;
import com.eshcherbinina.generalstore.dao.repository.ProductRepository;
import com.eshcherbinina.generalstore.dto.ProductDTO;
import com.eshcherbinina.generalstore.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    ProductRepository productRepository;
    @Mock
    private static MessageSource messageSource;

    @Before
    public void init() {
    }

    @Test
    public void getAllProductsTest() {
        List<Product> products = new ArrayList<>();
        Product prodOne = Product.builder()
                .id(3)
                .title("Nail gun")
                .price(23.45)
                .quantity(7)
                .build();
        Product prodTwo = Product.builder()
                .id(6)
                .title("Earbuds with active noise cancellation")
                .price(23.45)
                .quantity(7)
                .build();

        products.add(prodOne);
        products.add(prodTwo);
        when(productRepository.findAll()).thenReturn(products);

        List<ProductDTO> actualProducts = productService.getAllProducts();

        assertEquals(2, actualProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getProductByIdTest() {
        int id = 6;
        Product product = Product.builder()
                .id(id)
                .title("Earbuds with active noise cancellation")
                .price(23.45)
                .quantity(7)
                .build();

        when(productRepository.findById(6)).thenReturn(product);

        ProductDTO actualProduct = productService.getProduct(id);

        assertEquals(id, actualProduct.getId());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    public void AddNewProduct() {
        ProductDTO product = ProductDTO.builder()
                .id(6)
                .title("Earbuds with active noise cancellation")
                .price(23.45)
                .quantity(7)
                .build();

        productService.addNewProduct(product);

        verify(productRepository, times(1)).save(Mockito.any());
    }
}
