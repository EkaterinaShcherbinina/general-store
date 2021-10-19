package com.eshcherbinina.generalstore.restController;

import com.eshcherbinina.generalstore.dto.ProductDTO;
import com.eshcherbinina.generalstore.service.IProductService;
import com.eshcherbinina.generalstore.utils.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/product")
public class ProductController {

    private IProductService productService;
    private MessageSource messageSource;

    @Autowired
    public ProductController(IProductService productService,
                             MessageSource messageSource) {
        this.productService = productService;
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ProductDTO> getAll() {
        return productService.getAllProducts();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductDTO getProduct(@PathVariable long id) {
        return productService.getProduct(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newProduct(@Valid @RequestBody ProductDTO product) {
        SessionHelper.getCurrentUsername();
        productService.addNewProduct(product);
        return new ResponseEntity<>(messageSource.getMessage("api.response.product.creation.successful",
                null, Locale.ENGLISH), HttpStatus.CREATED);
    }
}
