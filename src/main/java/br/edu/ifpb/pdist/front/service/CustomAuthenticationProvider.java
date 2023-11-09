package br.edu.ifpb.pdist.front.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private String localhost = "http://localhost:50000";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Nome de usuário não encontrado: " + username);
        }
        String url = localhost + "/login"; 
        String parametrosLogin = username+password;
        
        
        String response = restTemplate.postForObject(url,parametrosLogin , String.class);
         

        System.err.println(response+ "volteiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");

        // Realize a validação personalizada da senha aqui
        if (passwordIsValid(password, userDetails.getPassword())) {
            // Se a senha for válida, crie uma instância de UsernamePasswordAuthenticationToken para autenticar o usuário
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        } else {
            throw new AuthenticationException("Senha inválida") {};
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private boolean passwordIsValid(String rawPassword, String encodedPassword) {
        // Implemente a validação segura da senha (por exemplo, usando BCrypt ou outro algoritmo de hash seguro).
        // Este é um exemplo simples e não seguro.
        return rawPassword.equals(encodedPassword);
    }
}
