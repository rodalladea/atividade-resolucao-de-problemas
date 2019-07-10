/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.DisciplinaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.ProfessorControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.RequerimentoControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Requerimento;
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
public class RequerimentoServico {
    private final RequerimentoControle requerimentoCon;
    private final ProfessorControle professorCon;
    private final DisciplinaControle disciplinaCon;
    
    @Autowired
    public RequerimentoServico(RequerimentoControle requerimentoCon, ProfessorControle professorCon,
                                DisciplinaControle disciplinaCon) {
        this.requerimentoCon = requerimentoCon;
        this.professorCon = professorCon;
        this.disciplinaCon = disciplinaCon;
    }
    
    @GetMapping ("/servico/requerimento")
    public ResponseEntity<List<Requerimento>> listar() {
        return ResponseEntity.ok(requerimentoCon.listaTodos());
    }
    
    @GetMapping ("/servico/requerimento/{id}")
    public ResponseEntity<Requerimento> listarPorId(@PathVariable Long id) {
        Requerimento requerimento = requerimentoCon.listaPorId(id);
        if (requerimento != null) {
            return ResponseEntity.ok(requerimentoCon.listaPorId(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping ("/servico/requerimento/chefia{idProfessor}")
    public ResponseEntity<List<Requerimento>> listarPorId(@PathVariable Long idProfessor,
                                                            @RequestBody Long idDisciplina) {
        Professor professor = professorCon.listaPorId(idProfessor);
        Disciplina disciplina = disciplinaCon.listaPorId(idDisciplina);
        
        List<Requerimento> requerimentos = requerimentoCon.requerimentosChefia(professor, disciplina);
        if (requerimentos != null) {
            return ResponseEntity.ok(requerimentos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping ("/servico/requerimento/incompleto{idProfessor}")
    public ResponseEntity<List<Requerimento>> listarIncompleto(@PathVariable Long idProfessor,
                                                            @RequestBody Long idDisciplina) {
        Professor professor = professorCon.listaPorId(idProfessor);
        Disciplina disciplina = disciplinaCon.listaPorId(idDisciplina);
        
        List<Requerimento> requerimentos = requerimentoCon.requerimentosIncompleto(professor, disciplina);
        if (requerimentos != null) {
            return ResponseEntity.ok(requerimentos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping ("/servico/requerimento")
    public ResponseEntity<Requerimento> criar(@RequestBody Requerimento requerimento) {
        requerimentoCon.salva(requerimento);
        return ResponseEntity.status(201).body(requerimento);
    }
    
    @DeleteMapping ("/servico/requerimento/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        Requerimento requerimento = requerimentoCon.listaPorId(id);
        if (requerimento != null) {
            requerimentoCon.exclui(requerimento);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping ("/servico/requerimento/{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody Requerimento requerimento) {
        Requerimento r = requerimentoCon.listaPorId(id);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }
        
        requerimentoCon.salva(requerimento);
        return ResponseEntity.noContent().build();
    }
}
