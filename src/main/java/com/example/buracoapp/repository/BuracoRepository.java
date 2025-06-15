package com.example.buracoapp.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.buracoapp.model.entity.Buraco;
public interface BuracoRepository extends JpaRepository<Buraco, Long> {}