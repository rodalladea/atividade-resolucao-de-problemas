/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.ProfessorControle;
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
public class ProfessorServico {
    private final ProfessorControle professorCon;
    
    @Autowired
    public ProfessorServico(ProfessorControle professorCon) {
        this.professorCon = professorCon;
    }
    
    @GetMapping ("/servico/professor")
    public ResponseEntity<List<Professor>> listar() {
        return ResponseEntity.ok(professorCon.listaTodos());
    }
    
    @GetMapping ("/servico/professor/{id}")
    public ResponseEntity<Professor> listarPorId(@PathVariable Long id) {
        Professor professor = professorCon.listaPorId(id);
        if (professor != null) {
            return ResponseEntity.ok(professorCon.listaPorId(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping ("/servico/professor/faltantes")
    public ResponseEntity<List<Professor>> listarFaltantes() {
        List<Professor> professores = professorCon.listaProfessoresFaltantes();
        if (professores != null) {
            return ResponseEntity.ok(professores);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping ("/servico/professor")
    public ResponseEntity<Professor> criar(@RequestBody Professor professor) {
        professorCon.salva(professor);
        return ResponseEntity.status(201).body(professor);
    }
    
    @DeleteMapping ("/servico/professor/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        Professor professor = professorCon.listaPorId(id);
        if (professor != null) {
            professorCon.exclui(professor);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping ("/servico/professor/{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody Professor professor) {
        Professor p = professorCon.listaPorId(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        
        professorCon.salva(professor);
        return ResponseEntity.noContent().build();
    }
}
