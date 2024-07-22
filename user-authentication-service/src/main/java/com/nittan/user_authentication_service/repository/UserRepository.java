package com.nittan.user_authentication_service.repository;

import com.nittan.user_authentication_service.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for interacting with UserCredential entities in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<UserCredential,Integer> {

    /**
     * Find a user by their username.
     * @param username The username to search for
     * @return Optional containing UserCredential if found, empty otherwise
     */
    Optional<UserCredential> findByName(String username);

    /**
     * Find a user by their email.
     * @param email The email to search for
     * @return Optional containing UserCredential if found, empty otherwise
     */
    UserCredential findByEmail(String email);
}
