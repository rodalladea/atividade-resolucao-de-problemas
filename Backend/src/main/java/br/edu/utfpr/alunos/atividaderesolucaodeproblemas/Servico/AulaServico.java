/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.AlunoControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.AulaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.DisciplinaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.ProfessorControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.AlunoDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.AulaDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.DisciplinaDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.RequerimentoDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aluno;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Estados;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Formas;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Requerimento;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Tipo;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.excecoes.ExceptionMaxDia;
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
public class AulaServico {
    private final AulaControle aulaCon;
    private final ProfessorControle professorCon;
    private final DisciplinaControle disciplinaCon;
    private final AlunoControle alunoCon;
    
    @Autowired
    public AulaServico(AulaControle aulaCon, ProfessorControle professorCon,
                        DisciplinaControle disciplinaCon, AlunoControle alunoCon) {
        this.aulaCon = aulaCon;
        this.professorCon = professorCon;
        this.disciplinaCon = disciplinaCon;  
        this.alunoCon = alunoCon;
    }
    
    @GetMapping ("/servico/aula")
    public ResponseEntity<List<Aula>> listar() {
        return ResponseEntity.ok(aulaCon.listaTodos());
    }
    
    @GetMapping ("/servico/aula/faltantes")
    public ResponseEntity<List<Aula>> listarFaltantes(@RequestBody RequerimentoDTO requerimentoDto) {
        Professor professor = professorCon.listaPorId(requerimentoDto.getProfessorId());
        Disciplina disciplina = disciplinaCon.listaPorId(requerimentoDto.getDisciplinaId());
        
        return ResponseEntity.ok(aulaCon.getAulasFaltantes(requerimentoDto.getDataInicio(),
                                                        requerimentoDto.getDataFim(), 
                                                        professor,
                                                        disciplina));
    }
    
    @GetMapping ("/servico/aula/esquecidas")
    public ResponseEntity<List<Aula>> listarEsquecidas() {
        return ResponseEntity.ok(aulaCon.getAulasEsquecidas());
    }
    
    @GetMapping ("/servico/aula/disciplina")
    public ResponseEntity<List<Aula>> listarDisciplina(@RequestBody DisciplinaDTO disciplinaDto) {
        Disciplina disciplina = disciplinaCon.listaPorId(disciplinaDto.getId());
        
        return ResponseEntity.ok(aulaCon.getAulasDisciplina(disciplina));
    }
    
    @GetMapping ("/servico/aula/comaluno")
    public ResponseEntity<List<Aula>> listarComAluno(@RequestBody AlunoDTO alunoDto) {
        Aluno aluno = alunoCon.listaPorId(alunoDto.getId());
        
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
    public ResponseEntity<Aula> criar(@RequestBody AulaDTO aulaDto) throws ExceptionMaxDia {
        Aula aula = new Aula();
        
        aula.setDataHora(aulaDto.getDataHora());
        
        List<Aluno> alunos = new ArrayList<>();
        for(Long a : aulaDto.getAlunosPresenteId()) {
            Aluno alunoPresente = new Aluno();
            alunoPresente = alunoCon.listaPorId(a);
            
            alunos.add(alunoPresente);
        }
        aula.setAlunosPresente(alunos);
        
        Disciplina disciplinaAluno = disciplinaCon.listaPorId(aulaDto.getDisciplinaId());
        aula.setDisciplina(disciplinaAluno);
        
        aula.setForma(aulaDto.getForma());
        aula.setEstado(aulaDto.getEstado());
        aula.setTipo(aulaDto.getTipo());
        
        Professor professorAluno = professorCon.listaPorId(aulaDto.getProfessorId());
        aula.setProfessor(professorAluno);
        
        aulaCon.salva(aula);
        return ResponseEntity.status(201).body(aula);
    }
    
    @PostMapping ("/servico/aula/todas")
    public ResponseEntity<List<Aula>> criarTodas(@RequestBody List<AulaDTO> aulasDto) {
        
        List<Aula> aulas = new ArrayList<>();
        
        for(AulaDTO aulaDto : aulasDto) {
            Aula aula = new Aula();
        
            aula.setDataHora(aulaDto.getDataHora());

            List<Aluno> alunos = new ArrayList<>();
            for(Long a : aulaDto.getAlunosPresenteId()) {
                Aluno alunoPresente = new Aluno();
                alunoPresente = alunoCon.listaPorId(a);

                alunos.add(alunoPresente);
            }
            aula.setAlunosPresente(alunos);

            Disciplina disciplinaAluno = disciplinaCon.listaPorId(aulaDto.getDisciplinaId());
            aula.setDisciplina(disciplinaAluno);

            aula.setForma(aulaDto.getForma());
            aula.setEstado(aulaDto.getEstado());
            aula.setTipo(aulaDto.getTipo());

            Professor professorAluno = professorCon.listaPorId(aulaDto.getProfessorId());
            aula.setProfessor(professorAluno);
            
            aulas.add(aula);
        }
        
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
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody AulaDTO aulaDto) throws ExceptionMaxDia {
        Aula a = aulaCon.listaPorId(id);
        if (a == null) {
            return ResponseEntity.notFound().build();
        }
        
        Aula aula = new Aula();
        
        aula.setDataHora(aulaDto.getDataHora());
        
        List<Aluno> alunos = new ArrayList<>();
        for(Long al : aulaDto.getAlunosPresenteId()) {
            Aluno alunoPresente = new Aluno();
            alunoPresente = alunoCon.listaPorId(al);
            
            alunos.add(alunoPresente);
        }
        aula.setAlunosPresente(alunos);
        
        Disciplina disciplinaAluno = disciplinaCon.listaPorId(aulaDto.getDisciplinaId());
        aula.setDisciplina(disciplinaAluno);
        
        aula.setForma(aulaDto.getForma());
        aula.setEstado(aulaDto.getEstado());
        aula.setTipo(aulaDto.getTipo());
        
        Professor professorAluno = professorCon.listaPorId(aulaDto.getProfessorId());
        aula.setProfessor(professorAluno);
        
        aulaCon.salva(aula);
        return ResponseEntity.noContent().build();
    }
}
