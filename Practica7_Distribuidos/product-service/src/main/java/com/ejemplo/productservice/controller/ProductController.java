package com.ejemplo.productservice.controller;

import com.ejemplo.productservice.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", 800.0));
        products.add(new Product(2, "Mouse", 20.0));
        products.add(new Product(3, "Teclado", 25.0));
        return products;
    }
}
