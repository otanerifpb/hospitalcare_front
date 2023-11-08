package br.edu.ifpb.pdist.front.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pdist.front.model.Medico;

@Controller
@RequestMapping("/login")
public class LoginController {
    
    // Rota para acessar o formulário pela Url
    @GetMapping
    public ModelAndView login() {
        return new ModelAndView("login/formLogin");
    }

    // Rota para acessar o Formunário pelo Url
    @RequestMapping("/formLogin")
    public ModelAndView getFormLogin(ModelAndView mav) {
        mav.setViewName("login/formLogin");
        return mav;
    }

    // Rota para acessar o Formunário Cadastro pelo Url
    //@RequestMapping("/formCadastro")
    @RequestMapping(value="/formCadastro", method = RequestMethod.GET)
    public ModelAndView getFormCadstro(ModelAndView mav) {
        mav.setViewName("login/formCadastro");
        return mav;
    }

    // Rota para Login
    @PostMapping
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password, ModelAndView mav) {
        // Valida os dados do formulário
        if (!username.equals("admin") || !password.equals("123456")) {
            //mav.setViewName("login", "error", "Usuário ou senha inválidos");
            //return new ModelAndView("login", "error", "Usuário ou senha inválidos");
            //return new ModelAndView("login/formCadastro");
            return new ModelAndView("redirect:/login");
        }

        // Cria um token de autenticação
        String token = UUID.randomUUID().toString();

        // Armazena o token no banco de dados
        // ...(SGBD)

        // Armazena o token na sessão
        // ...(Redis)

        // Redireciona para a página principal
        //return new ModelAndView("redirect:http://localhost:5080/");
        return new ModelAndView("redirect:/home");
        
    }
}
