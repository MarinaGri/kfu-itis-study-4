package ru.itis.services;

import ru.itis.dto.ProductDto;

import java.util.List;

public interface ProductsService {

     void addProduct(ProductDto product);

     List<ProductDto> getProducts();

     void updateProduct(ProductDto product);

     void deleteProduct(Long id);

}
