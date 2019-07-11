/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controller;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Dirgrad;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.up.DirgradDTO;
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
public class DirgradController {
    @GetMapping("/dirgrad")
    public String inicial(Model data) throws JsonSyntaxException, UnirestException {

        Dirgrad arrayDirgrads[] = new Gson()
                    .fromJson(
                        Unirest
                            .get("http://localhost:8081/servico/dirgrad")
                            .asJson()
                            .getBody()
                            .toString(), 
                        Dirgrad[].class
                    );

        data.addAttribute("dirgrads", arrayDirgrads);

        return "dirgrad-view";
    }

    @PostMapping ("/dirgrad/criar")
    public String criar(DirgradDTO dirgradDto) throws UnirestException {

            Unirest.post("http://localhost:8081/servico/dirgrad")
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(dirgradDto, DirgradDTO.class))
                .asJson();

        return "redirect:/dirgrad";
    }

    @GetMapping ("/dirgrad/excluir")
    public String excluir (@RequestParam Long id) throws UnirestException {

        Unirest
            .delete("http://localhost:8081/servico/dirgrad/{id}")
            .routeParam("id", String.valueOf(id))
            .asJson();

        return "redirect:/dirgrad";
    }

    @GetMapping ("/dirgrad/prepara-alterar")
    public String preparaAlterar (@RequestParam int id, Model data) throws JsonSyntaxException, UnirestException {

        Dirgrad dirgradExistente = new Gson()
            .fromJson(
                Unirest
                    .get("http://localhost:8081/servico/dirgrad/{id}")
                    .routeParam("id", String.valueOf(id))
                    .asJson()
                    .getBody()
                    .toString(),
                Dirgrad.class
            );

        data.addAttribute("dirgradAtual", dirgradExistente);

        Dirgrad arrayDirgrads[] = new Gson()
        .fromJson(
            Unirest
                .get("http://localhost:8081/servico/dirgrad")
                .asJson()
                .getBody()
                .toString(), 
            Dirgrad[].class
        );

        data.addAttribute("dirgrads", arrayDirgrads);

        return "dirgrad-view-alterar";
    }

    @PostMapping ("/dirgrad/alterar")
    public String alterar (DirgradDTO dirgradAlterado) throws UnirestException {

        Unirest
            .put("http://localhost:8081/servico/dirgrad/{id}")
            .routeParam("id", String.valueOf(dirgradAlterado.getId()))
            .header("Content-type", "application/json")
            .header("accept", "application/json")
            .body(new Gson().toJson(dirgradAlterado, DirgradDTO.class))
            .asJson();

        return "redirect:/dirgrad";
    }
}
