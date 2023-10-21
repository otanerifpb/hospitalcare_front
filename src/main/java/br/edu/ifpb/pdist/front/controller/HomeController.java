package br.edu.ifpb.pdist.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    // Ativa o menu Home na barra de navegação
    @ModelAttribute("menu")
    public String activeMenu(){
        return "home";
    }

    // Mapeamento para o index
     @RequestMapping("/home")
    public String showHomrPage(){
        return "index";
    }
}
