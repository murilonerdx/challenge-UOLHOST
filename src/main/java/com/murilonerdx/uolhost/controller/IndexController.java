package com.murilonerdx.uolhost.controller;

import com.murilonerdx.uolhost.model.Hero;
import com.murilonerdx.uolhost.model.Jogador;
import com.murilonerdx.uolhost.repository.JogadorRepository;
import com.murilonerdx.uolhost.service.ParamRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

  @Autowired private JogadorRepository repository;

  @Autowired private ParamRequestService service;

  @GetMapping("/")
  public String index(Jogador jogador) {
    return "index";
  }

  @PostMapping("/")
  public ModelAndView save(
      @ModelAttribute("jogador") Jogador jogador,
      @ModelAttribute("checkbox") String checkbox) {

    ModelAndView model = new ModelAndView("index");

    if (checkbox.equals("Vingadores")) {
      Hero myHero = service.sortedVingadores();
      if (myHero == null) {
        model.addObject("error", true);
        return model;
      }
      jogador.setHero(myHero);
      repository.save(jogador);
    } else if (checkbox.equals("Liga da Justiça")) {
      Hero myHero = service.sortedLigaDaJustica();
      if (myHero == null) {
        model.addObject("error", true);
        return model;
      }

      jogador.setHero(myHero);
      repository.save(jogador);
    }

    return model;
  }

  @GetMapping(value="/listar")
    public String list(Model mv){
    mv.addAttribute("jogadores", repository.findAll());
    return "listPeoples";
  }

  @GetMapping(value="/deletar/{id}")
  public String delete(@PathVariable("id") Long id){
    repository.deleteById(Long.valueOf(id));
    return "redirect:/listar";
  }

  @GetMapping(value="/editar/{id}")
  public ModelAndView edits(@PathVariable("id") Long id, Model model){
    Jogador jogador = repository.findById(id).get();
    return new ModelAndView("update").addObject("jogador",jogador);
  }

  @PostMapping("update/{id}")
  public String update(@PathVariable("id") Long id, @ModelAttribute("jogador") Jogador jogador,
                              Model model) {

    Jogador jogadorNew = repository.findById(id).get();
    jogadorNew.setEmail(jogador.getEmail());
    jogadorNew.setTelefone(jogador.getTelefone());
    jogadorNew.setNome(jogador.getNome());


    repository.save(jogadorNew);
    return "redirect:/listar";
  }
}
