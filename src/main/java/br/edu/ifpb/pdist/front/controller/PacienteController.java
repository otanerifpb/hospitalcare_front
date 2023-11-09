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

import br.edu.ifpb.pdist.front.model.Paciente;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    // Injeção de dependência
    @Autowired
    private RestTemplate restTemplate;

    private String localhost = "http://localhost:8082/paciente";
    
    // Ativa o menu na barra de navegação
    @ModelAttribute("menu")
    public String activeMenu(){
        return "paciente";
    }

    // Rota para acessar a lista pelo menu
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listAll(ModelAndView mav) {
        List<Paciente> opPacientes = restTemplate.getForObject(localhost, List.class);      
        mav.addObject("paciente", opPacientes);
        mav.setViewName("paciente/listPaciente");
        return mav;
    }

    // Rota para acessar o formunário pelo botão Novo
    @RequestMapping("/formPaciente")
    public ModelAndView getFormEstu(Paciente paciente, ModelAndView mav) {
        mav.addObject("paciente", paciente);
        mav.setViewName("paciente/formPaciente");
        return mav;
    }

    // Rota para cadastrar no Sitema pelo botão Salvar do formulário
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute Paciente paciente , ModelAndView mav, RedirectAttributes redAttrs) {
        String url = localhost + "/save";
        ResponseEntity<Paciente> response = restTemplate.postForEntity(url, paciente, Paciente.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            mav.addObject("succesMensagem", "Paciente " +response.getBody().getNome()+ "cadastrado com sucesso!");
            listAll(mav);
            mav.setViewName("paciente/listPaciente");
        } else {
            redAttrs.addFlashAttribute("errorMensagem", "Paciente já cadastrado!");
            mav.setViewName("redirect:/paciente");
        }
        return mav;
    }

    // Rota para preencer os dados do formunlário de update pelo botão Editar 
    @RequestMapping("/{id}")
    public ModelAndView getPacienteById(@PathVariable(value = "id") Integer id, ModelAndView mav) { 
        String url = localhost + "/" + id; 
        ResponseEntity<Paciente> response = restTemplate.getForEntity(url, Paciente.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Paciente paciente =  response.getBody();
            mav.addObject("paciente", paciente);
            mav.setViewName("paciente/formUpPaciente");
        } else {
            mav.addObject("errorMensagem", "Paciente  não encontrado.");
            mav.setViewName("paciente/listPaciente");
        }
        return mav;
    }

    // Rota para atualizar na lista pelo botão Salvar do formulário de update
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public ModelAndView updade(Paciente paciente, ModelAndView mav, RedirectAttributes redAttrs) {
        String url = localhost + "/update";
        ResponseEntity<Paciente> response = restTemplate.postForEntity(url, paciente, Paciente.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            mav.addObject("succesMensagem", "Paciente " +response.getBody().getNome()+ ", atualizado com sucesso!");
            listAll(mav);
            mav.setViewName("paciente/listPaciente");
        } else {
            redAttrs.addFlashAttribute("errorMensagem", "Paciente não atualizado!");
            mav.setViewName("redirect:/paciente");
        }
        return mav;
    }

    // Rota para deletar na lista pelo botão Excluir
    @RequestMapping("{id}/delete")
    public ModelAndView deleteById(@PathVariable(value = "id") Integer id, ModelAndView mav, RedirectAttributes redAttrs) {
        try {
            String url = localhost + "/delete/" + id;
            restTemplate.delete(url);
            redAttrs.addFlashAttribute("succesMensagem", "Paciente id: " +id+ " deletado com sucesso!!");
        } catch (HttpClientErrorException e) {
            redAttrs.addFlashAttribute("errorMensagem", "Paciente Não exluido!!");
        } 
        mav.setViewName("redirect:/paciente");
        return mav;
    }
}
