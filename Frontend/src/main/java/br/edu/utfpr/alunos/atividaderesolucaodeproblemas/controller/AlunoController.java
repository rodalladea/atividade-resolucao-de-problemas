/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controller;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Aluno;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.up.AlunoDTO;
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
public class AlunoController {
    @GetMapping("/aluno")
    public String inicial(Model data) throws JsonSyntaxException, UnirestException {

        Aluno arrayAlunos[] = new Gson()
                    .fromJson(
                        Unirest
                            .get("http://localhost:8081/servico/aluno")
                            .asJson()
                            .getBody()
                            .toString(), 
                        Aluno[].class
                    );

        data.addAttribute("alunos", arrayAlunos);

        return "aluno-view";
    }

    @PostMapping ("/aluno/criar")
    public String criar(AlunoDTO alunoDto) throws UnirestException {

            Unirest.post("http://localhost:8081/servico/aluno")
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(alunoDto, AlunoDTO.class))
                .asJson();

        return "redirect:/aluno";
    }

    @GetMapping ("/aluno/excluir")
    public String excluir (@RequestParam Long id) throws UnirestException {

        Unirest
            .delete("http://localhost:8081/servico/aluno/{id}")
            .routeParam("id", String.valueOf(id))
            .asJson();

        return "redirect:/aluno";
    }

    @GetMapping ("/aluno/prepara-alterar")
    public String preparaAlterar (@RequestParam int id, Model data) throws JsonSyntaxException, UnirestException {

        Aluno alunoExistente = new Gson()
            .fromJson(
                Unirest
                    .get("http://localhost:8081/servico/aluno/{id}")
                    .routeParam("id", String.valueOf(id))
                    .asJson()
                    .getBody()
                    .toString(),
                Aluno.class
            );

        data.addAttribute("alunoAtual", alunoExistente);
        data.addAttribute("disciplinas", alunoExistente.getDisciplinas());

        Aluno arrayAlunos[] = new Gson()
        .fromJson(
            Unirest
                .get("http://localhost:8081/servico/aluno")
                .asJson()
                .getBody()
                .toString(), 
            Aluno[].class
        );

        data.addAttribute("alunos", arrayAlunos);

        return "aluno-view-alterar";
    }

    @PostMapping ("/aluno/alterar")
    public String alterar (AlunoDTO alunoAlterado) throws UnirestException {

        Unirest
            .put("http://localhost:8081/servico/aluno/{id}")
            .routeParam("id", String.valueOf(alunoAlterado.getId()))
            .header("Content-type", "application/json")
            .header("accept", "application/json")
            .body(new Gson().toJson(alunoAlterado, AlunoDTO.class))
            .asJson();

        return "redirect:/aluno";
    }
}
