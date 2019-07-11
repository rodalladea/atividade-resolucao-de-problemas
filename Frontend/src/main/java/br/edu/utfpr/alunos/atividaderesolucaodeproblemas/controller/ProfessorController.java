/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controller;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.up.ProfessorDTO;
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
public class ProfessorController {
    @GetMapping("/professor")
    public String inicial(Model data) throws JsonSyntaxException, UnirestException {

        Professor arrayProfessores[] = new Gson()
                    .fromJson(
                        Unirest
                            .get("http://localhost:8081/servico/professor")
                            .asJson()
                            .getBody()
                            .toString(), 
                        Professor[].class
                    );

        data.addAttribute("professores", arrayProfessores);

        return "professor-view";
    }

    @PostMapping ("/professor/criar")
    public String criar(ProfessorDTO professorDto) throws UnirestException {

            Unirest.post("http://localhost:8081/servico/professor")
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(professorDto, ProfessorDTO.class))
                .asJson();

        return "redirect:/professor";
    }

    @GetMapping ("/professor/excluir")
    public String excluir (@RequestParam Long id) throws UnirestException {

        Unirest
            .delete("http://localhost:8081/servico/professor/{id}")
            .routeParam("id", String.valueOf(id))
            .asJson();

        return "redirect:/professor";
    }

    @GetMapping ("/professor/prepara-alterar")
    public String preparaAlterar (@RequestParam int id, Model data) throws JsonSyntaxException, UnirestException {

        Professor professorExistente = new Gson()
            .fromJson(
                Unirest
                    .get("http://localhost:8081/servico/professor/{id}")
                    .routeParam("id", String.valueOf(id))
                    .asJson()
                    .getBody()
                    .toString(),
                Professor.class
            );

        data.addAttribute("professorAtual", professorExistente);

        Professor arrayProfessors[] = new Gson()
        .fromJson(
            Unirest
                .get("http://localhost:8081/servico/professor")
                .asJson()
                .getBody()
                .toString(), 
            Professor[].class
        );

        data.addAttribute("professors", arrayProfessors);

        return "professor-view-alterar";
    }

    @PostMapping ("/professor/alterar")
    public String alterar (ProfessorDTO professorAlterado) throws UnirestException {

        Unirest
            .put("http://localhost:8081/servico/professor/{id}")
            .routeParam("id", String.valueOf(professorAlterado.getId()))
            .header("Content-type", "application/json")
            .header("accept", "application/json")
            .body(new Gson().toJson(professorAlterado, ProfessorDTO.class))
            .asJson();

        return "redirect:/professor";
    }

    @GetMapping("/professor/preparacriarequerimento")
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

        data.addAttribute("disciplinas", arrayProfessores);

        return "criarequerimento-view";
    }

    @PostMapping ("/professor/criarequerimento")
    public String alterar (CriaRequerimentoDTO crDto) throws UnirestException {

        Unirest
            .put("http://localhost:8081/servico/professor/criarequerimento")
            .header("Content-type", "application/json")
            .header("accept", "application/json")
            .body(new Gson().toJson(crDto, CriaRequerimentoDTO.class))
            .asJson();

        return "redirect:/professor";
    }
    
}
