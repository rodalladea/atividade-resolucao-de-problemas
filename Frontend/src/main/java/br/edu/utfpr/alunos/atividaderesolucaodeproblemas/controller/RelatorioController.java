/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controller;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Relatorio;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.up.RelatorioDTO;
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
public class RelatorioController {
    @GetMapping("/relatorio")
    public String inicial(Model data) throws JsonSyntaxException, UnirestException {

        Relatorio arrayRelatorios[] = new Gson()
                    .fromJson(
                        Unirest
                            .get("http://localhost:8081/servico/relatorio")
                            .asJson()
                            .getBody()
                            .toString(), 
                        Relatorio[].class
                    );

        data.addAttribute("relatorios", arrayRelatorios);

        return "relatorio-view";
    }

    @PostMapping ("/relatorio/criar")
    public String criar(RelatorioDTO relatorioDto) throws UnirestException {

            Unirest.post("http://localhost:8081/servico/relatorio")
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(relatorioDto, RelatorioDTO.class))
                .asJson();

        return "redirect:/relatorio";
    }

    @GetMapping ("/relatorio/excluir")
    public String excluir (@RequestParam Long id) throws UnirestException {

        Unirest
            .delete("http://localhost:8081/servico/relatorio/{id}")
            .routeParam("id", String.valueOf(id))
            .asJson();

        return "redirect:/relatorio";
    }

    @GetMapping ("/relatorio/prepara-alterar")
    public String preparaAlterar (@RequestParam int id, Model data) throws JsonSyntaxException, UnirestException {

        Relatorio relatorioExistente = new Gson()
            .fromJson(
                Unirest
                    .get("http://localhost:8081/servico/relatorio/{id}")
                    .routeParam("id", String.valueOf(id))
                    .asJson()
                    .getBody()
                    .toString(),
                Relatorio.class
            );

        data.addAttribute("relatorioAtual", relatorioExistente);

        Relatorio arrayRelatorios[] = new Gson()
        .fromJson(
            Unirest
                .get("http://localhost:8081/servico/relatorio")
                .asJson()
                .getBody()
                .toString(), 
            Relatorio[].class
        );

        data.addAttribute("relatorios", arrayRelatorios);

        return "relatorio-view-alterar";
    }

    @PostMapping ("/relatorio/alterar")
    public String alterar (RelatorioDTO relatorioAlterado) throws UnirestException {

        Unirest
            .put("http://localhost:8081/servico/relatorio/{id}")
            .routeParam("id", String.valueOf(relatorioAlterado.getId()))
            .header("Content-type", "application/json")
            .header("accept", "application/json")
            .body(new Gson().toJson(relatorioAlterado, RelatorioDTO.class))
            .asJson();

        return "redirect:/relatorio";
    }
}
