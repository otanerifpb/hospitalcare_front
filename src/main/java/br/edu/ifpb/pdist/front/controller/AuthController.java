package br.edu.ifpb.pdist.front.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pdist.front.model.User;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;

   // private String localhost = "http://localhost:8085"; //backuser
    // private String localhost = "http://localhost:8086"; //local gatewayconfig
    private String urlWeb    =  "https://gatewayhospital-0433a88d53ad.herokuapp.com";
    // Rota para acessar o formLogin geral
    @GetMapping
    public ModelAndView login() {
        return new ModelAndView("auth/formLogin");
    }

    // // Rota para acessar o FormLogin
    // @RequestMapping("/formLogin")
    // public ModelAndView getFormLogin(ModelAndView mav) {
    //     mav.setViewName("auth/formLogin");
    //     return mav;
    // }

    // Rota para acessar o FormCadastro
    @RequestMapping("/formCadastro")
    public ModelAndView getFormCadastro(User user, ModelAndView mav) {
        mav.addObject("user", user);
        mav.setViewName("auth/formCadastro");
        return mav;
    }

    // Rota para realizar o Login
    //@PostMapping
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
        //return new ModelAndView("redirect:http://localhost:5000/");
        
        String url = urlWeb + "/login"; 
        String parametrosLogin = username+password;
        String response = restTemplate.postForObject(url,parametrosLogin , String.class);
        //ResponseEntity<User> response = restTemplate.postForObject(url,parametrosLogin , User.class);

        // if (response.getStatusCode() == HttpStatus.OK) {

        // }

        System.err.println("\nResposta do Login: " +response);
        return new ModelAndView("redirect:/home");
    }

    // Rota para realizar o Cadastrar
    //  @PostMapping
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute User usuario , ModelAndView mav) {

        String url = urlWeb + "/user/save";
        ResponseEntity<User> response = restTemplate.postForEntity(url, usuario, User.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            //System.err.println(response + "\nEstou no if do Cadastro");
            //return new ModelAndView("redirect:/login");
            mav.addObject("succesMensagem", "Usuário " +response.getBody().getNome()+ " Cadastrado com Sucesso!");
            mav.setViewName("redirect:/login");
            //mav.setViewName("/auth/formCadastro");
        } else {
            //System.err.println(response + "\nEstou no else do Cadastro");
            mav.addObject("errorMensagem", "E-mail " +usuario.getUsername()+ " já Cadastrado no Sistema!");
            mav.setViewName("/auth/formCadastro");
        }
        //System.err.println(response + "volte");
        //return new ModelAndView("redirect:/login");
        return mav;
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
        return new ModelAndView("erros/404");
    }
}
