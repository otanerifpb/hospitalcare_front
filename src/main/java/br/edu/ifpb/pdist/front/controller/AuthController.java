package br.edu.ifpb.pdist.front.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;

    private String localhost = "http://localhost:5000";
    // Rota para acessar o formLogin geral
    @GetMapping
    public ModelAndView login() {
        return new ModelAndView("auth/formLogin");
    }

    // Rota para acessar o FormLogin
    @RequestMapping("/formLogin")
    public ModelAndView getFormLogin(ModelAndView mav) {
        mav.setViewName("auth/formLogin");
        return mav;
    }

    // Rota para acessar o FormCadastro
    //@RequestMapping("/formCadastro")
    @RequestMapping(value="/formCadastro")
    public ModelAndView getFormCadstro(ModelAndView mav) {
        mav.setViewName("auth/formCadastro");
        return mav;
    }

    // Rota para realizar o Login
  //  @PostMapping
  @RequestMapping(method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password, ModelAndView mav) {
        // Valida os dados do formulário
        // if (!username.equals("renato") || !password.equals("123456")) {
        //     //mav.setViewName("login", "error", "Usuário ou senha inválidos");
        //     //return new ModelAndView("login", "error", "Usuário ou senha inválidos");
        //     //return new ModelAndView("login/formCadastro");
        //     return new ModelAndView("redirect:/auth");
        // }
        

        // Cria um token de autenticação
       // String token = UUID.randomUUID().toString();

        // Armazena o token no banco de dados
        // ...(SGBD)

        // Armazena o token na sessão
        // ...(Redis)

        // Redireciona para a página principal
        //return new ModelAndView("redirect:http://localhost:5080/");
        
          String url = localhost + "/login"; 
          String parametrosLogin = username+password;
        
        
        String response = restTemplate.postForObject(url,parametrosLogin , String.class);
         

        System.err.println(response+ "volteiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        
        
            return new ModelAndView("redirect:/home");
            
        }
    
    // Rota para realizar o Logout
    @RequestMapping("/logout")
    public ModelAndView logout(ModelAndView mav, HttpSession session) {
        session.invalidate();
        mav.setViewName("redirect:/auth");
        return mav;
    }

    // Rota para páginas em desenvolvimento
   @RequestMapping("/obra")
    public ModelAndView getForm(ModelAndView mav) {  
        //mav.setViewName("/login/formLogin");
        //return mav;
        return new ModelAndView("erros/404");
    }
}
