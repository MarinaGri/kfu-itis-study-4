package ru.itis.cms.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itis.cms.models.Role;
import ru.itis.cms.models.UserAuthority;

import java.util.Optional;

public interface UserAuthoritiesRepository extends CrudRepository<UserAuthority, Integer> {

    Optional<UserAuthority> findByAuthority(Role authority);

}
