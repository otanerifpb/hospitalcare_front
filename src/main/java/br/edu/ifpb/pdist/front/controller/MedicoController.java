package br.edu.ifpb.pdist.front.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pdist.front.model.Medico;

@Controller
@RequestMapping("/medico")
public class MedicoController {

    // Injeção de dependência
    @Autowired
    private RestTemplate restTemplate;

    // Ativa o menu Médico na barra de navegação
    @ModelAttribute("menu")
    public String activeMenu(){
        return "medico";
    }

    // Rota para acessar a lista pelo menu
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listAll(ModelAndView mav) {
        List<Medico> opMedicos = restTemplate.getForObject("http://localhost:8081/medico", List.class);       
        mav.addObject("medico", opMedicos);
        mav.setViewName("medico/listMedico");
        return mav;
    } 

    // Rota para acessar a lista ao usar o REDIRECT
    // @RequestMapping()
    // public String listAll(Model model) {
    //     model.addAttribute("medico", medicoRepository.findAll());
    //     return "medico/listMedico";
    // }

    // // Rota para acessar o formunário
    @RequestMapping("/formMedico")
    public ModelAndView getFormEstu(Medico medico, ModelAndView mav) {
        mav.addObject("medico", medico);
        mav.setViewName("medico/formMedico");
        return mav;
    }

    // Rota para cadastrar um Médico no Sitema
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute Medico medico , ModelAndView mav, RedirectAttributes redAttrs) {
        ResponseEntity<Medico> response = restTemplate.postForEntity("http://localhost:8081/medico/save", medico, Medico.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            mav.addObject("succesMensagem", "Médico cadastrado com sucesso!");
            listAll(mav);
            mav.setViewName("medico/listMedico");
        } else {
            mav.addObject("errorMensagem", "Médico já cadastrado!");
            listAll(mav);
            mav.setViewName("medico/listMedico");
        }
        return mav;
    }
        

    // Rota para preencer os dados do formunlário de atualização com dados do banco 
    @RequestMapping("/{id}")
    public ModelAndView getMedicoById(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        String url = "http://localhost:8081/medico/"+id; // Substitua 1 pelo ID desejado.
        ResponseEntity<Medico> response = restTemplate.getForEntity(url, Medico.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Medico medico =  response.getBody();
            mav.addObject("medico", medico);
            mav.setViewName("medico/formUpMedico");
        } else {
            mav.addObject("errorMensagem", "Médico  não encontrado.");
            mav.setViewName("medico/listMedico");
        }
        return mav;
    }

    // // Rota para atualizar um Médico na lista pelo formUpMedico
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public ModelAndView updade(Medico medico, ModelAndView mav, RedirectAttributes redAttrs) {
        ResponseEntity<Medico> response = restTemplate.postForEntity("http://localhost:8081/medico/update", medico, Medico.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            mav.addObject("succesMensagem", "Medico "+response.getBody().getNome()+", atualizado com sucesso!");
            listAll(mav);
            mav.setViewName("medico/listMedico");
        } else {
            mav.addObject("errorMensagem", "Médico não atualizado!");
            listAll(mav);
            mav.setViewName("medico/listMedico");
        }
        mav.setViewName("/medico/listMedico"); 
        return mav;
    }

    // Rota para deletar um Médico da lista
   @RequestMapping("{id}/delete")
    public ModelAndView deleteById(@PathVariable(value = "id") Integer id, ModelAndView mav, RedirectAttributes redAttrs) {
        try {
            String url = "http://localhost:8081" + "/medico/delete/" + id;
            restTemplate.delete(url);
            redAttrs.addFlashAttribute("succesMensagem", "Medico id: "+id+" deletado com sucesso!!");
        } catch (HttpClientErrorException e) {
            redAttrs.addFlashAttribute("errorMensagem", "Medico Não exluido!!");
        } 
        mav.setViewName("redirect:/medico");
        return mav;
    }
}
