package br.edu.ifpb.pdist.front.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private String localhost = "http://localhost:8085";

    @Autowired
    private RestTemplate restTemplate;
  
    public UserDetails loadUserByUsername(String username,String password) throws UsernameNotFoundException {

        return null;
        // Implemente a lógica para carregar os detalhes do usuário aqui
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return null;
        // Implemente a lógica para carregar os detalhes do usuário aqui
    }
}
