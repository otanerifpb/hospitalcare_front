package br.edu.ifpb.pdist.front.service;

import java.util.List;

import javax.persistence.Embeddable;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
public class  AuthenticationResponse {
    private String username;
    private List<GrantedAuthority> authorities;

}
