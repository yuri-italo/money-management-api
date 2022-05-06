package br.com.alura.moneymanagementapi.repository;

import br.com.alura.moneymanagementapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    List<User> findByEmailContainingIgnoreCase(String email);
}
