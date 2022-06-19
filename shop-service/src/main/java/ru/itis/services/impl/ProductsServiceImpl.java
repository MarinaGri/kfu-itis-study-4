package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dto.ProductDto;
import ru.itis.exceptions.ProductNotFoundException;
import ru.itis.models.Product;
import ru.itis.repositories.ProductsRepository;
import ru.itis.services.ProductsService;

import static ru.itis.dto.ProductDto.from;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;

    @Override
    public void addProduct(ProductDto product) {
        Product newProduct = Product.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
        productsRepository.save(newProduct);

    }

    @Override
    public List<ProductDto> getProducts() {
        return from(productsRepository.findAll().orElseThrow(ProductNotFoundException::new));
    }

    @Override
    public void updateProduct(ProductDto product) {
        Product newProduct = Product.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
        productsRepository.update(newProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productsRepository.delete(id);
    }

}
