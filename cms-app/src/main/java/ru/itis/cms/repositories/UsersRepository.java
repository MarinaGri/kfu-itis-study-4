package ru.itis.cms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.cms.models.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
