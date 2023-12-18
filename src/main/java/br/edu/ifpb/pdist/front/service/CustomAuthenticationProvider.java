package br.edu.ifpb.pdist.front.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    private String localhost = "http://localhost:8085";
    //private String localhost = "https://gatewayhospital-0433a88d53ad.herokuapp.com/user";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
 
        String url = localhost + "/user/logar";
        MultiValueMap<String, String> parametros = new LinkedMultiValueMap<>();
        parametros.add("param1", username);
        parametros.add("param2", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parametros, headers);

        AuthenticationResponse  resultado = restTemplate.postForObject(url, request, AuthenticationResponse.class);
        System.out.println(authentication);

        // Crie um Authentication usando a implementação apropriada (por exemplo, UsernamePasswordAuthenticationToken)
        Authentication authenticationRes = new UsernamePasswordAuthenticationToken(resultado.getUsername(), password, resultado.getAuthorities());
        
        return authenticationRes;
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
