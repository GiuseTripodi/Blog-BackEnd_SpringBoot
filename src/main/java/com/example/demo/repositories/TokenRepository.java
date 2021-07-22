package com.example.demo.repositories;

import com.example.demo.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    boolean existsTokenByAccesAndIdarticolo(String accessToken, String idArticolo);
}
