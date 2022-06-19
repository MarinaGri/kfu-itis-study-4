package ru.itis.repositories;

import ru.itis.models.Product;


import java.util.List;
import java.util.Optional;

public interface ProductsRepository {

    void save(Product product);

    Optional<List<Product>> findAll();

    void delete(Long id);

    void update(Product product);
}
