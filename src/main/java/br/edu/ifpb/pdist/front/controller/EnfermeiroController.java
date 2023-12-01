package br.edu.ifpb.pdist.front.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pdist.front.model.Enfermeiro;

@Controller
@RequestMapping("/enfermeiro")
public class EnfermeiroController {

     // Injeção de dependência
    @Autowired
    private RestTemplate restTemplate;

     private String localhost = "http://localhost:8085/enfermeiro";
   //private String localhost = "https://gatewayhospital-0433a88d53ad.herokuapp.com/enfermeiro";
    
    // Ativa o menu na barra de navegação
    @ModelAttribute("menu")
    public String activeMenu(){
        return "enfermeiro";
    }

    // Rota para acessar a lista pelo menu
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listAll(ModelAndView mav) {
        //String url = localhost + "/enfermeiro";
        //List<Enfermeiro> opEnfermeiros = restTemplate.getForObject("http://localhost:8083/enfermeiro", List.class); 
        List<Enfermeiro> opEnfermeiros = restTemplate.getForObject(localhost, List.class);      
        mav.addObject("enfermeiro", opEnfermeiros);
        mav.setViewName("enfermeiro/listEnfermeiro");
        return mav;
    } 

    // Rota para acessar o formunário pelo botão Novo
    //System.err.println("Entrei&&&&&&&&&&&&&&&&&Rota/formEnfermeiro&&&&&&&&&&&&&&&&&&&&&&&&&");
    @RequestMapping("/formEnfermeiro")
    public ModelAndView getFormEnfermeiro(Enfermeiro enfermeiro, ModelAndView mav) {
        mav.addObject("enfermeiro", enfermeiro);
        mav.setViewName("enfermeiro/formEnfermeiro");
        return mav;
    }

    // Rota para cadastrar no Sitema pelo botão Salvar do formulário
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute Enfermeiro enfermeiro , ModelAndView mav, RedirectAttributes redAttrs) {
        String url = localhost + "/save";
        //ResponseEntity<Enfermeiro> response = restTemplate.postForEntity("http://localhost:8083/enfermeiro/save", enfermeiro, Enfermeiro.class);
        ResponseEntity<Enfermeiro> response = restTemplate.postForEntity(url, enfermeiro, Enfermeiro.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            mav.addObject("succesMensagem", "Enfermeiro" +response.getBody().getNome()+ " cadastrado com sucesso!");
            listAll(mav);
            mav.setViewName("enfermeiro/listEnfermeiro");
        } else {
            redAttrs.addFlashAttribute("errorMensagem", "Enfermeiro já cadastrado!");
            mav.setViewName("redirect:/enfermeiro");
        }
        return mav;
    }

    // Rota para preencer os dados do formunlário de update pelo botão Editar
    @RequestMapping("/{id}")
    public ModelAndView getEnfermeiroById(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        //String url = "http://localhost:8083/enfermeiro/" + id; 
        String url = localhost + "/" + id; 
        ResponseEntity<Enfermeiro> response = restTemplate.getForEntity(url, Enfermeiro.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Enfermeiro enfermeiro =  response.getBody();
            mav.addObject("enfermeiro", enfermeiro);
            mav.setViewName("enfermeiro/formUpEnfermeiro");
        } else {
            mav.addObject("errorMensagem", "Enfermeiro  não encontrado.");
            mav.setViewName("enfermeiro/listEnfermeiro");
        }
        return mav;
    }

    // Rota para atualizar na lista pelo botão Salvar do formulário de update
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public ModelAndView updade(Enfermeiro enfermeiro, ModelAndView mav, RedirectAttributes redAttrs) {
        String url = localhost + "/update";
        //ResponseEntity<Enfermeiro> response = restTemplate.postForEntity("http://localhost:8083/enfermeiro/update", medico, Medico.class);
        ResponseEntity<Enfermeiro> response = restTemplate.postForEntity(url, enfermeiro, Enfermeiro.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            mav.addObject("succesMensagem", "Enfermeiro " +response.getBody().getNome()+ ", atualizado com sucesso!");
            listAll(mav);
            mav.setViewName("enfermeiro/listEnfermeiro");
        } else {
            redAttrs.addFlashAttribute("errorMensagem", "Enfermeiro não atualizado!");
            mav.setViewName("redirect:/enfermeiro");
        }
        return mav;
    }

    // Rota para deletar na lista pelo botão Excluir
    @RequestMapping("{id}/delete")
    public ModelAndView deleteById(@PathVariable(value = "id") Integer id, ModelAndView mav, RedirectAttributes redAttrs) {
        try {
            String url = localhost + "/delete/" + id;
            restTemplate.delete(url);
            redAttrs.addFlashAttribute("succesMensagem", "Enfermeiro id: " +id+ " deletado com sucesso!!");
        } catch (HttpClientErrorException e) {
            redAttrs.addFlashAttribute("errorMensagem", "Enfermeiro Não exluido!!");
        } 
        mav.setViewName("redirect:/enfermeiro");
        return mav;
    }
}
