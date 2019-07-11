/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controller;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.SemestreAcademico;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.up.SemestreAcademicoDTO;
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
public class SemestreAcademicoController {
    @GetMapping("/semestreAcademico")
    public String inicial(Model data) throws JsonSyntaxException, UnirestException {

        SemestreAcademico arraySemestres[] = new Gson()
                    .fromJson(
                        Unirest
                            .get("http://localhost:8081/servico/semestreAcademico")
                            .asJson()
                            .getBody()
                            .toString(), 
                        SemestreAcademico[].class
                    );

        data.addAttribute("semestreAcademicos", arraySemestres);

        return "semestreAcademico-view";
    }

    @PostMapping ("/semestreAcademico/criar")
    public String criar(SemestreAcademicoDTO semestreAcademicoDto) throws UnirestException {

            Unirest.post("http://localhost:8081/servico/semestreAcademico")
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(semestreAcademicoDto, SemestreAcademicoDTO.class))
                .asJson();

        return "redirect:/semestreAcademico";
    }

    @GetMapping ("/semestreAcademico/excluir")
    public String excluir (@RequestParam Long id) throws UnirestException {

        Unirest
            .delete("http://localhost:8081/servico/semestreAcademico/{id}")
            .routeParam("id", String.valueOf(id))
            .asJson();

        return "redirect:/semestreAcademico";
    }

    @GetMapping ("/semestreAcademico/prepara-alterar")
    public String preparaAlterar (@RequestParam int id, Model data) throws JsonSyntaxException, UnirestException {

        SemestreAcademico semestreAcademicoExistente = new Gson()
            .fromJson(
                Unirest
                    .get("http://localhost:8081/servico/semestreAcademico/{id}")
                    .routeParam("id", String.valueOf(id))
                    .asJson()
                    .getBody()
                    .toString(),
                SemestreAcademico.class
            );

        data.addAttribute("semestreAcademicoAtual", semestreAcademicoExistente);

        SemestreAcademico arraySemestreAcademicos[] = new Gson()
        .fromJson(
            Unirest
                .get("http://localhost:8081/servico/semestreAcademico")
                .asJson()
                .getBody()
                .toString(), 
            SemestreAcademico[].class
        );

        data.addAttribute("semestreAcademicos", arraySemestreAcademicos);

        return "semestreAcademico-view-alterar";
    }

    @PostMapping ("/semestreAcademico/alterar")
    public String alterar (SemestreAcademicoDTO semestreAcademicoAlterado) throws UnirestException {

        Unirest
            .put("http://localhost:8081/servico/semestreAcademico/{id}")
            .routeParam("id", String.valueOf(semestreAcademicoAlterado.getId()))
            .header("Content-type", "application/json")
            .header("accept", "application/json")
            .body(new Gson().toJson(semestreAcademicoAlterado, SemestreAcademicoDTO.class))
            .asJson();

        return "redirect:/semestreAcademico";
    }
}
