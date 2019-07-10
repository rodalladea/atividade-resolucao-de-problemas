/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.AulaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aluno;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Requerimento;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.excecoes.ExceptionMaxDia;
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
public class AulaServico {
    private final AulaControle aulaCon;
    
    @Autowired
    public AulaServico(AulaControle aulaCon) {
        this.aulaCon = aulaCon;
    }
    
    @GetMapping ("/servico/aula")
    public ResponseEntity<List<Aula>> listar() {
        return ResponseEntity.ok(aulaCon.listaTodos());
    }
    
    @GetMapping ("/servico/aula/faltantes")
    public ResponseEntity<List<Aula>> listarFaltantes(@RequestBody Requerimento requerimento) {
        return ResponseEntity.ok(aulaCon.getAulasFaltantes(requerimento.getDataInicio(),
                                                        requerimento.getDataFim(), 
                                                        requerimento.getProfessor(),
                                                        requerimento.getDisciplina()));
    }
    
    @GetMapping ("/servico/aula/esquecidas")
    public ResponseEntity<List<Aula>> listarEsquecidas() {
        return ResponseEntity.ok(aulaCon.getAulasEsquecidas());
    }
    
    @GetMapping ("/servico/aula/disciplina")
    public ResponseEntity<List<Aula>> listarDisciplina(@RequestBody Disciplina disciplina) {
        return ResponseEntity.ok(aulaCon.getAulasDisciplina(disciplina));
    }
    
    @GetMapping ("/servico/aula/comaluno")
    public ResponseEntity<List<Aula>> listarComAluno(@RequestBody Aluno aluno) {
        return ResponseEntity.ok(aulaCon.getAulasComAluno(aluno));
    }
    
    @GetMapping ("/servico/aula/{id}")
    public ResponseEntity<Aula> listarPorId(@PathVariable Long id) {
        Aula aula = aulaCon.listaPorId(id);
        if (aula != null) {
            return ResponseEntity.ok(aulaCon.listaPorId(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping ("/servico/aula")
    public ResponseEntity<Aula> criar(@RequestBody Aula aula) throws ExceptionMaxDia {
        aulaCon.salva(aula);
        return ResponseEntity.status(201).body(aula);
    }
    
    @PostMapping ("/servico/aula/todas")
    public ResponseEntity<List<Aula>> criarTodas(@RequestBody List<Aula> aulas) {
        aulaCon.salvaTodas(aulas);
        return ResponseEntity.status(201).body(aulas);
    }
    
    @DeleteMapping ("/servico/aula/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        Aula aula = aulaCon.listaPorId(id);
        if (aula != null) {
            aulaCon.exclui(aula);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping ("/servico/aula/{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody Aula aula) throws ExceptionMaxDia {
        Aula a = aulaCon.listaPorId(id);
        if (a == null) {
            return ResponseEntity.notFound().build();
        }
        
        aulaCon.salva(aula);
        return ResponseEntity.noContent().build();
    }
}
