/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.AlunoControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.AulaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.ChefiaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.DisciplinaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.ProfessorControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.RequerimentoControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.RequerimentoDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.RequerimentosChefiaDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aluno;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Falta;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Requerimento;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Status;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Tipo;
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
public class RequerimentoServico {
    private final RequerimentoControle requerimentoCon;
    private final ProfessorControle professorCon;
    private final DisciplinaControle disciplinaCon;
    private final ChefiaControle chefiaCon;
    private final AulaControle aulaCon;
    private final AlunoControle alunoCon;
    
    @Autowired
    public RequerimentoServico(RequerimentoControle requerimentoCon, ProfessorControle professorCon,
                                DisciplinaControle disciplinaCon, ChefiaControle chefiaCon,
                                AulaControle aulaCon, AlunoControle alunoCon) {
        this.requerimentoCon = requerimentoCon;
        this.professorCon = professorCon;
        this.disciplinaCon = disciplinaCon;
        this.chefiaCon = chefiaCon;
        this.aulaCon = aulaCon;
        this.alunoCon = alunoCon;
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
    
    @GetMapping ("/servico/requerimento/chefia")
    public ResponseEntity<List<Requerimento>> listarChefiaProfessor(
            @RequestBody RequerimentosChefiaDTO rcDto) {
        Professor professor = professorCon.listaPorId(rcDto.getProfessorId());
        Disciplina disciplina = disciplinaCon.listaPorId(rcDto.getDisciplinaId());
        
        List<Requerimento> requerimentos = requerimentoCon.requerimentosChefia(professor, disciplina);
        if (requerimentos != null) {
            return ResponseEntity.ok(requerimentos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping ("/servico/requerimento/incompleto{idProfessor}")
    public ResponseEntity<List<Requerimento>> listarIncompleto(
            @RequestBody RequerimentosChefiaDTO rcDto) {
        Professor professor = professorCon.listaPorId(rcDto.getProfessorId());
        Disciplina disciplina = disciplinaCon.listaPorId(rcDto.getDisciplinaId());
        
        List<Requerimento> requerimentos = requerimentoCon.requerimentosIncompleto(professor, disciplina);
        if (requerimentos != null) {
            return ResponseEntity.ok(requerimentos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping ("/servico/requerimento")
    public ResponseEntity<Requerimento> criar(@RequestBody RequerimentoDTO requerimentoDto) {
        Requerimento requerimento = new Requerimento();
        
        requerimento.setDataInicio(requerimentoDto.getDataInicio());
        requerimento.setDataFim(requerimentoDto.getDataFim());
        
        Professor professor = professorCon.listaPorId(requerimentoDto.getProfessorId());
        requerimento.setProfessor(professor);
        
        Chefia chefia = chefiaCon.listaPorId(requerimentoDto.getChefiaId());
        requerimento.setChefia(chefia);
        
        Disciplina disciplina = disciplinaCon.listaPorId(requerimentoDto.getDisciplinaId());
        requerimento.setDisciplina(disciplina);
        
        List<Aula> aulasFaltantes = new ArrayList<>();
        for(Long af : requerimentoDto.getAulasFaltantesId()) {
            Aula aulaFaltante = new Aula();
            
            aulaFaltante = aulaCon.listaPorId(af);
            
            aulasFaltantes.add(aulaFaltante);
        }
        requerimento.setAulasFaltantes(aulasFaltantes);
        
        List<Aula> aulasReposicao = new ArrayList<>();
        for(Long ar : requerimentoDto.getAulasReposicaoId()) {
            Aula aulaReposicao = new Aula();
            
            aulaReposicao = aulaCon.listaPorId(ar);
            
            aulasReposicao.add(aulaReposicao);
        }
        requerimento.setAulasReposicao(aulasReposicao);
        
        List<Aluno> listaAnuencia = new ArrayList<>();
        for(Long an : requerimentoDto.getListaAnuenciaId()) {
            Aluno aluno = new Aluno();
            
            aluno = alunoCon.listaPorId(an);
            
            listaAnuencia.add(aluno);
        }
        requerimento.setListaAnuencia(listaAnuencia);
        
        requerimento.setTipo(requerimentoDto.getTipo());
        requerimento.setStatus(requerimentoDto.getStatus());
        requerimento.setFalta(requerimentoDto.getFalta());
       
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
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody RequerimentoDTO requerimentoDto) {
        Requerimento r = requerimentoCon.listaPorId(id);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }
        
        Requerimento requerimento = new Requerimento();
        
        requerimento.setDataInicio(requerimentoDto.getDataInicio());
        requerimento.setDataFim(requerimentoDto.getDataFim());
        
        Professor professor = professorCon.listaPorId(requerimentoDto.getProfessorId());
        requerimento.setProfessor(professor);
        
        Chefia chefia = chefiaCon.listaPorId(requerimentoDto.getChefiaId());
        requerimento.setChefia(chefia);
        
        Disciplina disciplina = disciplinaCon.listaPorId(requerimentoDto.getDisciplinaId());
        requerimento.setDisciplina(disciplina);
        
        List<Aula> aulasFaltantes = new ArrayList<>();
        for(Long af : requerimentoDto.getAulasFaltantesId()) {
            Aula aulaFaltante = new Aula();
            
            aulaFaltante = aulaCon.listaPorId(af);
            
            aulasFaltantes.add(aulaFaltante);
        }
        requerimento.setAulasFaltantes(aulasFaltantes);
        
        List<Aula> aulasReposicao = new ArrayList<>();
        for(Long ar : requerimentoDto.getAulasReposicaoId()) {
            Aula aulaReposicao = new Aula();
            
            aulaReposicao = aulaCon.listaPorId(ar);
            
            aulasReposicao.add(aulaReposicao);
        }
        requerimento.setAulasReposicao(aulasReposicao);
        
        List<Aluno> listaAnuencia = new ArrayList<>();
        for(Long an : requerimentoDto.getListaAnuenciaId()) {
            Aluno aluno = new Aluno();
            
            aluno = alunoCon.listaPorId(an);
            
            listaAnuencia.add(aluno);
        }
        requerimento.setListaAnuencia(listaAnuencia);
        
        requerimento.setTipo(requerimentoDto.getTipo());
        requerimento.setStatus(requerimentoDto.getStatus());
        requerimento.setFalta(requerimentoDto.getFalta());
        
        requerimentoCon.salva(requerimento);
        return ResponseEntity.noContent().build();
    }
}
