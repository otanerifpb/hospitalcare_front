package br.edu.ifpb.pdist.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    // Rota para acessar o formunário pelo botão Novo
    @RequestMapping("/formLogin")
    public ModelAndView getFormLogin(ModelAndView mav) {
        mav.setViewName("login/formLogin");
        return mav;
    }
}
