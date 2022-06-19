package ru.itis.repositories.jdbc_template;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.models.Product;
import ru.itis.repositories.ProductsRepository;

import javax.sql.DataSource;
import java.util.*;

@Profile("jdbc_template")
@Repository
public class ProductsRepositoryJdbcTemplateImpl implements ProductsRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from product order by id";

    //language=SQL
    private static final String SQL_INSERT = "insert into product(name, price) values (:name, :price)";

    //language=SQL
    private static final String SQL_DELETE = "delete from product where id = :id";

    //language=SQL
    private static final String SQL_UPDATE = "update product set name = :name, price = :price where id = :id;";


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductsRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final RowMapper<Product> accountRowMapper = (row, rowNumber) -> ru.itis.models.Product.builder()
            .id(row.getLong("id"))
            .name(row.getString("name"))
            .price(row.getDouble("price"))
            .build();

    @Override
    public Optional<List<Product>> findAll() {
        return Optional.of(namedParameterJdbcTemplate.query(SQL_SELECT_ALL, accountRowMapper));
    }

    @Override
    public void save(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        Map<String, Object> values = new HashMap<>();
        values.put("name", product.getName());
        values.put("price", product.getPrice());

        SqlParameterSource parameterSource = new MapSqlParameterSource(values);

        namedParameterJdbcTemplate.update(SQL_INSERT, parameterSource, keyHolder, new String[]{"id"});

        product.setId(keyHolder.getKeyAs(Long.class));
    }

    @Override
    public void delete(Long id){
        Map<String, Object> values = Collections.singletonMap("id", id);
        namedParameterJdbcTemplate.update(SQL_DELETE, values);
    }

    @Override
    public void update(Product product){
        Map<String, Object> values = new HashMap<>();
        values.put("id", product.getId());
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        namedParameterJdbcTemplate.update(SQL_UPDATE, values);
    }

}
