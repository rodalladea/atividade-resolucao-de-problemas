/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.DisciplinaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
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
public class DisciplinaServico {
    private final DisciplinaControle disciplinaCon;
    
    @Autowired
    public DisciplinaServico(DisciplinaControle disciplinaCon) {
        this.disciplinaCon = disciplinaCon;
    }
    
    @GetMapping ("/servico/disciplina")
    public ResponseEntity<List<Disciplina>> listar() {
        return ResponseEntity.ok(disciplinaCon.listaTodos());
    }
    
    @GetMapping ("/servico/disciplina/{id}")
    public ResponseEntity<Disciplina> listarPorId(@PathVariable Long id) {
        Disciplina disciplina = disciplinaCon.listaPorId(id);
        if (disciplina != null) {
            return ResponseEntity.ok(disciplinaCon.listaPorId(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping ("/servico/disciplina")
    public ResponseEntity<Disciplina> criar(@RequestBody Disciplina disciplina) {
        disciplinaCon.salva(disciplina);
        return ResponseEntity.status(201).body(disciplina);
    }
    
    @DeleteMapping ("/servico/disciplina/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        Disciplina disciplina = disciplinaCon.listaPorId(id);
        if (disciplina != null) {
            disciplinaCon.exclui(disciplina);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping ("/servico/disciplina/{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody Disciplina disciplina) {
        Disciplina d = disciplinaCon.listaPorId(id);
        if (d == null) {
            return ResponseEntity.notFound().build();
        }
        
        disciplinaCon.salva(disciplina);
        return ResponseEntity.noContent().build();
    }
}
