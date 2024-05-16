package de.clearnote.iboknowsbest.clearnote.repository;

import de.clearnote.iboknowsbest.clearnote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * In dieser Klasse sind nur CRUD Operationen ohne Prüfung oder sonstiges.
 * Alles was weiteren Code Bedarf wird in dem UserService Interface festgelegt und
 * in der UserServiceImpl Klasse implementiert.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
}
