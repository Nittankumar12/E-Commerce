package com.nittan.user_authentication_service.repository;

import com.nittan.user_authentication_service.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserCredential,Integer> {

    Optional<UserCredential> findByName(String username);
}
