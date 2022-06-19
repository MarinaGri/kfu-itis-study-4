package ru.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.AccountDto;
import ru.itis.dto.ProductDto;
import ru.itis.services.AccountsService;
import ru.itis.services.ProductsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ShopController {

    private final AccountsService accountsService;

    private final ProductsService productsService;


    @PostMapping( "/products")
    public ResponseEntity<Object> addProduct(@RequestBody ProductDto product) {
        productsService.addProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping( "/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        return new ResponseEntity<>(productsService.getProducts(), HttpStatus.OK);
    }

    @PutMapping("/products/{product-id}")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDto product,
                                                @PathVariable("product-id") Long id) {
        product.setId(id);
        productsService.updateProduct(product);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/products/{product-id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("product-id") Long productId) {
        productsService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDto>> getByProductDiscountDayNumInCart(@RequestParam("dayNum") Integer dayNum) {
        return new ResponseEntity<>(accountsService.searchByProductDiscountDayNumInCart(dayNum), HttpStatus.OK);
    }
}
