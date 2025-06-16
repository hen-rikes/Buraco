package com.example.buracoapp.controller;

import com.example.buracoapp.model.entity.Buraco;
import com.example.buracoapp.repository.BuracoRepository;
import com.example.buracoapp.repository.PessoaRepository;
import com.example.buracoapp.model.entity.Pessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

// Imports necessários para a correção de fuso horário
import java.time.LocalDateTime; // <-- MUDANÇA: Usando LocalDateTime
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/buracos")
@RequiredArgsConstructor
public class BuracoController {

    private final BuracoRepository buracoRepository;
    private final PessoaRepository pessoaRepository;

    @GetMapping
    public List<Buraco> listarTodos() {
        return buracoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Buraco> cadastrar(
        @RequestParam("uf") String uf,
        @RequestParam("cidade") String cidade,
        @RequestParam("rua") String rua,
        @RequestParam("descricao") String descricao
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Pessoa autor = pessoaRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado para o email: " + userEmail));

        Buraco novoBuraco = new Buraco();
        novoBuraco.setUf(uf);
        novoBuraco.setCidade(cidade);
        novoBuraco.setRua(rua);
        novoBuraco.setDescricao(descricao);
        
        // --- CORREÇÃO FINAL APLICADA AQUI ---
        // Agora, pegamos a data E HORA especificamente para o fuso horário de São Paulo.
        // Isso evita a ambiguidade de fuso horário na camada de persistência.
        novoBuraco.setData(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        
        novoBuraco.setAutor(autor);
        
        Buraco buracoSalvo = buracoRepository.save(novoBuraco);
        return ResponseEntity.status(HttpStatus.CREATED).body(buracoSalvo);
    }
}
