/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controller;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.up.AulaDTO;
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
public class AulaController {
    @GetMapping("/aula")
    public String inicial(Model data) throws JsonSyntaxException, UnirestException {

        Aula arrayAulas[] = new Gson()
                    .fromJson(
                        Unirest
                            .get("http://localhost:8081/servico/aula")
                            .asJson()
                            .getBody()
                            .toString(), 
                        Aula[].class
                    );

        data.addAttribute("aulas", arrayAulas);

        return "aula-view";
    }

    @PostMapping ("/aula/criar")
    public String criar(AulaDTO aulaDto) throws UnirestException {

            Unirest.post("http://localhost:8081/servico/aula")
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(aulaDto, AulaDTO.class))
                .asJson();

        return "redirect:/aula";
    }

    @GetMapping ("/aula/excluir")
    public String excluir (@RequestParam Long id) throws UnirestException {

        Unirest
            .delete("http://localhost:8081/servico/aula/{id}")
            .routeParam("id", String.valueOf(id))
            .asJson();

        return "redirect:/aula";
    }

    @GetMapping ("/aula/prepara-alterar")
    public String preparaAlterar (@RequestParam int id, Model data) throws JsonSyntaxException, UnirestException {

        Aula aulaExistente = new Gson()
            .fromJson(
                Unirest
                    .get("http://localhost:8081/servico/aula/{id}")
                    .routeParam("id", String.valueOf(id))
                    .asJson()
                    .getBody()
                    .toString(),
                Aula.class
            );

        data.addAttribute("aulaAtual", aulaExistente);

        Aula arrayAulas[] = new Gson()
        .fromJson(
            Unirest
                .get("http://localhost:8081/servico/aula")
                .asJson()
                .getBody()
                .toString(), 
            Aula[].class
        );

        data.addAttribute("aulas", arrayAulas);

        return "aula-view-alterar";
    }

    @PostMapping ("/aula/alterar")
    public String alterar (AulaDTO aulaAlterado) throws UnirestException {

        Unirest
            .put("http://localhost:8081/servico/aula/{id}")
            .routeParam("id", String.valueOf(aulaAlterado.getId()))
            .header("Content-type", "application/json")
            .header("accept", "application/json")
            .body(new Gson().toJson(aulaAlterado, AulaDTO.class))
            .asJson();

        return "redirect:/aula";
    }
}
