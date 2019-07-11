/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.AlunoControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.DisciplinaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.AlunoDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aluno;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
import java.util.ArrayList;
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
public class AlunoServico {
    private final AlunoControle alunoCon;
    private final DisciplinaControle disciplinaCon;
    
    @Autowired
    public AlunoServico(AlunoControle alunoCon, DisciplinaControle disciplinaCon) {
        this.alunoCon = alunoCon;
        this.disciplinaCon = disciplinaCon;
    }
    
    @GetMapping ("/servico/aluno")
    public ResponseEntity<List<Aluno>> listar() {
        return ResponseEntity.ok(alunoCon.listaTodos());
    }
    
    @GetMapping ("/servico/aluno/{id}")
    public ResponseEntity<Aluno> listarPorId(@PathVariable Long id) {
        Aluno aluno = alunoCon.listaPorId(id);
        if (aluno != null) {
            return ResponseEntity.ok(alunoCon.listaPorId(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping ("/servico/aluno")
    public ResponseEntity<Aluno> criar(@RequestBody AlunoDTO alunoDto) {
        List<Disciplina> disciplinas = new ArrayList<>();
        for(Long d : alunoDto.getDisciplinasId()) {
            Disciplina disciplina = disciplinaCon.listaPorId(d);
            
            disciplinas.add(disciplina);
        }
        
        Aluno a = new Aluno();
        
        a.setNome(alunoDto.getNome());
        a.setPorcentagemPresenca(alunoDto.getPorcentagemPresenca());
        a.setDisciplinas(disciplinas);
        
        alunoCon.salva(a);
        return ResponseEntity.status(201).body(a);
    }
    
    @DeleteMapping ("/servico/aluno/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        Aluno aluno = alunoCon.listaPorId(id);
        if (aluno != null) {
            alunoCon.exclui(aluno);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping ("/servico/aluno/{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody AlunoDTO alunoDto) {
        Aluno a = alunoCon.listaPorId(id);
        if (a == null) {
            return ResponseEntity.notFound().build();
        }
        
        List<Disciplina> disciplinas = new ArrayList<>();
        for(Long d : alunoDto.getDisciplinasId()) {
            Disciplina disciplina = disciplinaCon.listaPorId(d);
            
            disciplinas.add(disciplina);
        }
        
        Aluno al = new Aluno();
        
        a.setNome(alunoDto.getNome());
        a.setPorcentagemPresenca(alunoDto.getPorcentagemPresenca());
        a.setDisciplinas(disciplinas);
        
        alunoCon.salva(al);
        return ResponseEntity.noContent().build();
    }
}
