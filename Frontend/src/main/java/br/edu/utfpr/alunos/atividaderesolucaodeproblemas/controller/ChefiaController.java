/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controller;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.up.ChefiaDTO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author rodrigo
 */
@Controller
public class ChefiaController {
    @GetMapping("/chefia")
    public String inicial(Model data) throws JsonSyntaxException, UnirestException {

        Chefia arrayChefias[] = new Gson()
                    .fromJson(
                        Unirest
                            .get("http://localhost:8081/servico/chefia")
                            .asJson()
                            .getBody()
                            .toString(), 
                        Chefia[].class
                    );

        data.addAttribute("chefias", arrayChefias);

        return "chefia-view";
    }

    @PostMapping ("/chefia/criar")
    public String criar(ChefiaDTO chefiaDto) throws UnirestException {

            Unirest.post("http://localhost:8081/servico/chefia")
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(chefiaDto, ChefiaDTO.class))
                .asJson();

        return "redirect:/chefia";
    }

    @GetMapping ("/chefia/excluir")
    public String excluir (@RequestParam Long id) throws UnirestException {

        Unirest
            .delete("http://localhost:8081/servico/chefia/{id}")
            .routeParam("id", String.valueOf(id))
            .asJson();

        return "redirect:/chefia";
    }

    @GetMapping ("/chefia/prepara-alterar")
    public String preparaAlterar (@RequestParam int id, Model data) throws JsonSyntaxException, UnirestException {

        Chefia chefiaExistente = new Gson()
            .fromJson(
                Unirest
                    .get("http://localhost:8081/servico/chefia/{id}")
                    .routeParam("id", String.valueOf(id))
                    .asJson()
                    .getBody()
                    .toString(),
                Chefia.class
            );

        data.addAttribute("chefiaAtual", chefiaExistente);

        Chefia arrayChefias[] = new Gson()
        .fromJson(
            Unirest
                .get("http://localhost:8081/servico/chefia")
                .asJson()
                .getBody()
                .toString(), 
            Chefia[].class
        );

        data.addAttribute("chefias", arrayChefias);

        return "chefia-view-alterar";
    }

    @PostMapping ("/chefia/alterar")
    public String alterar (ChefiaDTO chefiaAlterado) throws UnirestException {

        Unirest
            .put("http://localhost:8081/servico/chefia/{id}")
            .routeParam("id", String.valueOf(chefiaAlterado.getId()))
            .header("Content-type", "application/json")
            .header("accept", "application/json")
            .body(new Gson().toJson(chefiaAlterado, ChefiaDTO.class))
            .asJson();

        return "redirect:/chefia";
    }
}
