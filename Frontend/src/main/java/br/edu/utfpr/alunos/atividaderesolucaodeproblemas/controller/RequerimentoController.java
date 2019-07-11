/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controller;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Requerimento;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.up.RequerimentoDTO;
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
public class RequerimentoController {
    @GetMapping("/requerimento")
    public String inicial(Model data) throws JsonSyntaxException, UnirestException {

        Requerimento arrayRelatorios[] = new Gson()
                    .fromJson(
                        Unirest
                            .get("http://localhost:8081/servico/requerimento")
                            .asJson()
                            .getBody()
                            .toString(), 
                        Requerimento[].class
                    );

        data.addAttribute("requerimentos", arrayRelatorios);

        return "requerimento-view";
    }

    @PostMapping ("/requerimento/criar")
    public String criar(RequerimentoDTO requerimentoDto) throws UnirestException {

            Unirest.post("http://localhost:8081/servico/requerimento")
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(requerimentoDto, RequerimentoDTO.class))
                .asJson();

        return "redirect:/requerimento";
    }

    @GetMapping ("/requerimento/excluir")
    public String excluir (@RequestParam Long id) throws UnirestException {

        Unirest
            .delete("http://localhost:8081/servico/requerimento/{id}")
            .routeParam("id", String.valueOf(id))
            .asJson();

        return "redirect:/requerimento";
    }

    @GetMapping ("/requerimento/prepara-alterar")
    public String preparaAlterar (@RequestParam int id, Model data) throws JsonSyntaxException, UnirestException {

        Requerimento requerimentoExistente = new Gson()
            .fromJson(
                Unirest
                    .get("http://localhost:8081/servico/requerimento/{id}")
                    .routeParam("id", String.valueOf(id))
                    .asJson()
                    .getBody()
                    .toString(),
                Requerimento.class
            );

        data.addAttribute("requerimentoAtual", requerimentoExistente);

        Requerimento arrayRequerimentos[] = new Gson()
        .fromJson(
            Unirest
                .get("http://localhost:8081/servico/requerimento")
                .asJson()
                .getBody()
                .toString(), 
            Requerimento[].class
        );

        data.addAttribute("requerimentos", arrayRequerimentos);

        return "requerimento-view-alterar";
    }

    @PostMapping ("/requerimento/alterar")
    public String alterar (RequerimentoDTO requerimentoAlterado) throws UnirestException {

        Unirest
            .put("http://localhost:8081/servico/requerimento/{id}")
            .routeParam("id", String.valueOf(requerimentoAlterado.getId()))
            .header("Content-type", "application/json")
            .header("accept", "application/json")
            .body(new Gson().toJson(requerimentoAlterado, RequerimentoDTO.class))
            .asJson();

        return "redirect:/requerimento";
    }
}
