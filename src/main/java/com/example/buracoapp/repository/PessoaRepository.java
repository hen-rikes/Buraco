package com.example.buracoapp.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.buracoapp.model.entity.Pessoa;
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByEmail(String email);
}