package ru.itis.repositories.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, Long> {

    @Query("select account from Account account inner join account.cart product " +
            "where product.discount.dayNum = :dayNum")
    Optional<List<Account>> findAllByProductDiscountDayNum(@Param("dayNum") Integer dayNum);
}
