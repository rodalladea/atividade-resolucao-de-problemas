/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.ChefiaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rodrigo
 */
@RestController
public class ChefiaServico {
    private final ChefiaControle chefiaCon;
    
    @Autowired
    public ChefiaServico(ChefiaControle chefiaCon) {
        this.chefiaCon = chefiaCon;
    }
    
    @GetMapping ("/servico/chefia")
    public ResponseEntity<List<Chefia>> listar() {
        return ResponseEntity.ok(chefiaCon.listaTodos());
    }
    
    @GetMapping ("/servico/chefia/professor")
    public ResponseEntity<Chefia> listarPorId(@RequestBody Professor professor) {
        Chefia chefia = chefiaCon.getChefiaByProfessor(professor);
        if (chefia != null) {
            return ResponseEntity.ok(chefia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping ("/servico/chefia/{id}")
    public ResponseEntity<Chefia> listarPorId(@PathVariable Long id) {
        Chefia chefia = chefiaCon.listaPorId(id);
        if (chefia != null) {
            return ResponseEntity.ok(chefiaCon.listaPorId(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping ("/servico/chefia")
    public ResponseEntity<Chefia> criar(@RequestBody Chefia chefia) {
        chefiaCon.salva(chefia);
        return ResponseEntity.status(201).body(chefia);
    }
    
    @DeleteMapping ("/servico/chefia/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        Chefia chefia = chefiaCon.listaPorId(id);
        if (chefia != null) {
            chefiaCon.exclui(chefia);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping ("/servico/chefia/{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody Chefia chefia) {
        Chefia c = chefiaCon.listaPorId(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        
        chefiaCon.salva(chefia);
        return ResponseEntity.noContent().build();
    }
}
