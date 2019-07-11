/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controller;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.up.DisciplinaDTO;
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
public class DisciplinaController {
    @GetMapping("/disciplina")
    public String inicial(Model data) throws JsonSyntaxException, UnirestException {

        Disciplina arrayDisciplinas[] = new Gson()
                    .fromJson(
                        Unirest
                            .get("http://localhost:8081/servico/disciplina")
                            .asJson()
                            .getBody()
                            .toString(), 
                        Disciplina[].class
                    );

        data.addAttribute("disciplinas", arrayDisciplinas);

        return "disciplina-view";
    }

    @PostMapping ("/disciplina/criar")
    public String criar(DisciplinaDTO disciplinaDto) throws UnirestException {

            Unirest.post("http://localhost:8081/servico/disciplina")
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(disciplinaDto, DisciplinaDTO.class))
                .asJson();

        return "redirect:/disciplina";
    }

    @GetMapping ("/disciplina/excluir")
    public String excluir (@RequestParam Long id) throws UnirestException {

        Unirest
            .delete("http://localhost:8081/servico/disciplina/{id}")
            .routeParam("id", String.valueOf(id))
            .asJson();

        return "redirect:/disciplina";
    }

    @GetMapping ("/disciplina/prepara-alterar")
    public String preparaAlterar (@RequestParam int id, Model data) throws JsonSyntaxException, UnirestException {

        Disciplina disciplinaExistente = new Gson()
            .fromJson(
                Unirest
                    .get("http://localhost:8081/servico/disciplina/{id}")
                    .routeParam("id", String.valueOf(id))
                    .asJson()
                    .getBody()
                    .toString(),
                Disciplina.class
            );

        data.addAttribute("disciplinaAtual", disciplinaExistente);

        Disciplina arrayDisciplinas[] = new Gson()
        .fromJson(
            Unirest
                .get("http://localhost:8081/servico/disciplina")
                .asJson()
                .getBody()
                .toString(), 
            Disciplina[].class
        );

        data.addAttribute("disciplinas", arrayDisciplinas);

        return "disciplina-view-alterar";
    }

    @PostMapping ("/disciplina/alterar")
    public String alterar (DisciplinaDTO disciplinaAlterado) throws UnirestException {

        Unirest
            .put("http://localhost:8081/servico/disciplina/{id}")
            .routeParam("id", String.valueOf(disciplinaAlterado.getId()))
            .header("Content-type", "application/json")
            .header("accept", "application/json")
            .body(new Gson().toJson(disciplinaAlterado, DisciplinaDTO.class))
            .asJson();

        return "redirect:/disciplina";
    }
}
