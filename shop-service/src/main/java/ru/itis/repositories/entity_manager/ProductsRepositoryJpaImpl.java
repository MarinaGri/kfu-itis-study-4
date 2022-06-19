package ru.itis.repositories.entity_manager;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.itis.exceptions.ProductNotFoundException;
import ru.itis.models.Product;
import ru.itis.repositories.ProductsRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Profile("entity_manager")
@RequiredArgsConstructor
@Repository
public class ProductsRepositoryJpaImpl implements ProductsRepository {

    private final EntityManager entityManager;

    @Override
    public void save(Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(product);
        transaction.commit();
    }

    @Override
    public Optional<List<Product>> findAll() {
        TypedQuery<Product> query = entityManager.createQuery("select product from Product product", Product.class);
        return Optional.of(query.getResultList());
    }

    @Override
    public void delete(Long id) {
        Product product = entityManager.find(Product.class, id);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(product);
        transaction.commit();
    }

    @Override
    public void update(Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(product);
        transaction.commit();
    }
}
