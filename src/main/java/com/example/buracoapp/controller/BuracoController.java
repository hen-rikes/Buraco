package com.example.buracoapp.controller;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.buracoapp.model.entity.Buraco;
import com.example.buracoapp.model.entity.Pessoa;
import com.example.buracoapp.repository.BuracoRepository;
import com.example.buracoapp.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

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
        // @RequestParam(value = "imagem", required = false) MultipartFile imagem, // Upload de arquivo é complexo em ambientes sem estado.
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
        novoBuraco.setData(LocalDate.now());
        novoBuraco.setAutor(autor);
        
        Buraco buracoSalvo = buracoRepository.save(novoBuraco);
        return ResponseEntity.status(HttpStatus.CREATED).body(buracoSalvo);
    }
}