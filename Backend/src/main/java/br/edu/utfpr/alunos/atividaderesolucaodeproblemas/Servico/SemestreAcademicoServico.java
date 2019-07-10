/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.SemestreAcademicoControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.SemestreAcademico;
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
public class SemestreAcademicoServico {
    private final SemestreAcademicoControle semestreAcademicoCon;
    
    @Autowired
    public SemestreAcademicoServico(SemestreAcademicoControle semestreAcademicoCon) {
        this.semestreAcademicoCon = semestreAcademicoCon;
    }
    
    @GetMapping ("/servico/semestreAcademico")
    public ResponseEntity<List<SemestreAcademico>> listar() {
        return ResponseEntity.ok(semestreAcademicoCon.listaTodos());
    }
    
    @GetMapping ("/servico/semestreAcademico/{id}")
    public ResponseEntity<SemestreAcademico> listarPorId(@PathVariable Long id) {
        SemestreAcademico semestreAcademico = semestreAcademicoCon.listaPorId(id);
        if (semestreAcademico != null) {
            return ResponseEntity.ok(semestreAcademicoCon.listaPorId(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping ("/servico/semestreAcademico")
    public ResponseEntity<SemestreAcademico> criar(@RequestBody SemestreAcademico semestreAcademico) {
        semestreAcademicoCon.salva(semestreAcademico);
        return ResponseEntity.status(201).body(semestreAcademico);
    }
    
    @DeleteMapping ("/servico/semestreAcademico/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        SemestreAcademico semestreAcademico = semestreAcademicoCon.listaPorId(id);
        if (semestreAcademico != null) {
            semestreAcademicoCon.exclui(semestreAcademico);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping ("/servico/semestreAcademico/{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody SemestreAcademico semestreAcademico) {
        SemestreAcademico a = semestreAcademicoCon.listaPorId(id);
        if (a == null) {
            return ResponseEntity.notFound().build();
        }
        
        semestreAcademicoCon.salva(semestreAcademico);
        return ResponseEntity.noContent().build();
    }
}
