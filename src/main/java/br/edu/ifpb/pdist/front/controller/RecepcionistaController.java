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
import br.edu.ifpb.pdist.front.model.Recepcionista;

@Controller
@RequestMapping("/recepcionista")
public class RecepcionistaController {

    // Injeção de dependência
    @Autowired
    private RestTemplate restTemplate;
    
    private String localhost = "http://localhost:8086/recepcionista";

    // Ativa o menu na barra de navegação
    @ModelAttribute("menu")
    public String activeMenu(){
        return "recepcionista";
    }

    // Rota para acessar a lista pelo menu
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listAll(ModelAndView mav) {
        List<Recepcionista> opRecepcionistas = restTemplate.getForObject(localhost, List.class);      
        mav.addObject("recepcionista", opRecepcionistas);
        mav.setViewName("recepcionista/listRecepcionista");
        return mav;
    }

    // Rota para acessar o formunário pelo botão Novo
    @RequestMapping("/formRecepcionista")
    public ModelAndView getFormEstu(Recepcionista recepcionista, ModelAndView mav) {
        mav.addObject("recepcionista", recepcionista);
        mav.setViewName("recepcionista/formRecepcionista");
        return mav;
    }

    // Rota para cadastrar no Sitema pelo botão Salvar do formulário
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute Recepcionista recepcionista , ModelAndView mav, RedirectAttributes redAttrs) {
        String url = localhost + "/save";
        ResponseEntity<Recepcionista> response = restTemplate.postForEntity(url, recepcionista, Recepcionista.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            mav.addObject("succesMensagem", "Recepcionista " +response.getBody().getNome()+ "cadastrado com sucesso!");
            listAll(mav);
            mav.setViewName("recepcionista/listRecepcionista");
        } else {
            redAttrs.addFlashAttribute("errorMensagem", "Recepcionista já cadastrado!");
            mav.setViewName("redirect:/recepcionista");
        }
        return mav;
    }

    // Rota para preencer os dados do formunlário de update pelo botão Editar 
    @RequestMapping("/{id}")
    public ModelAndView getRecepcionistaById(@PathVariable(value = "id") Integer id, ModelAndView mav) { 
        String url = localhost + "/" + id; 
        ResponseEntity<Recepcionista> response = restTemplate.getForEntity(url, Recepcionista.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Recepcionista recepcionista =  response.getBody();
            mav.addObject("recepcionista", recepcionista);
            mav.setViewName("recepcionista/formUpRecepcionista");
        } else {
            mav.addObject("errorMensagem", "Recepcionista  não encontrado.");
            mav.setViewName("recepcionista/listRecepcionista");
        }
        return mav;
    }

    // Rota para atualizar na lista pelo botão Salvar do formulário de update
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public ModelAndView updade(Recepcionista recepcionista, ModelAndView mav, RedirectAttributes redAttrs) {
        String url = localhost + "/update";
        ResponseEntity<Recepcionista> response = restTemplate.postForEntity(url, recepcionista, Recepcionista.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            mav.addObject("succesMensagem", "Recepcionista " +response.getBody().getNome()+ ", atualizado com sucesso!");
            listAll(mav);
            mav.setViewName("recepcionista/listRecepcionista");
        } else {
            redAttrs.addFlashAttribute("errorMensagem", "Recepcionista não atualizado!");
            mav.setViewName("redirect:/recepcionista");
        }
        return mav;
    }

    // Rota para deletar na lista pelo botão Excluir
    @RequestMapping("{id}/delete")
    public ModelAndView deleteById(@PathVariable(value = "id") Integer id, ModelAndView mav, RedirectAttributes redAttrs) {
        try {
            String url = localhost + "/delete/" + id;
            restTemplate.delete(url);
            redAttrs.addFlashAttribute("succesMensagem", "Recepcionista id: " +id+ " deletado com sucesso!!");
        } catch (HttpClientErrorException e) {
            redAttrs.addFlashAttribute("errorMensagem", "Recepcionista Não exluido!!");
        } 
        mav.setViewName("redirect:/recepcionista");
        return mav;
    }
}
