package com.example.buracoapp.security;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.buracoapp.model.entity.Pessoa;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PessoaUserDetails implements UserDetails {
    private final Pessoa pessoa;
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptyList(); }
    @Override public String getPassword() { return pessoa.getSenha(); }
    @Override public String getUsername() { return pessoa.getEmail(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}