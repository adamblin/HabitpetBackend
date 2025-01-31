package com.habitpet.app.habitpetbackend.persistence;

import com.habitpet.app.habitpetbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String usernameId);
}
