/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.AlunoControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.DisciplinaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.ProfessorControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.SemestreAcademicoControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.DisciplinaDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aluno;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.SemestreAcademico;
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
public class DisciplinaServico {
    private final DisciplinaControle disciplinaCon;
    private final SemestreAcademicoControle semestreAcademicoCon;
    private final ProfessorControle professorCon;
    private final AlunoControle alunoCon;
    
    @Autowired
    public DisciplinaServico(DisciplinaControle disciplinaCon, 
                            SemestreAcademicoControle semestreAcademicoCon,
                            ProfessorControle professorCon,
                            AlunoControle alunoCon) {
        this.disciplinaCon = disciplinaCon;
        this.semestreAcademicoCon = semestreAcademicoCon;
        this.professorCon = professorCon;
        this.alunoCon = alunoCon;
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
    public ResponseEntity<Disciplina> criar(@RequestBody DisciplinaDTO disciplinaDto) {
        Disciplina disciplina = new Disciplina();
        
        disciplina.setNome(disciplinaDto.getNome());
        
        SemestreAcademico semestre = semestreAcademicoCon.listaPorId(disciplinaDto.getSemestreId());
        disciplina.setSemestre(semestre);
        
        Professor professor = professorCon.listaPorId(disciplinaDto.getProfessorId());
        
        List<Aluno> alunos = new ArrayList<>();
        for(Long a : disciplinaDto.getAlunosMatriculadosId()) {
            Aluno aluno = new Aluno();
            
            aluno = alunoCon.listaPorId(a);
            
            alunos.add(aluno);
        }
        disciplina.setAlunosMatriculados(alunos);
        
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
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaDto) {
        Disciplina d = disciplinaCon.listaPorId(id);
        if (d == null) {
            return ResponseEntity.notFound().build();
        }
        
        Disciplina disciplina = new Disciplina();
        
        disciplina.setNome(disciplinaDto.getNome());
        
        SemestreAcademico semestre = semestreAcademicoCon.listaPorId(disciplinaDto.getSemestreId());
        disciplina.setSemestre(semestre);
        
        Professor professor = professorCon.listaPorId(disciplinaDto.getProfessorId());
        
        List<Aluno> alunos = new ArrayList<>();
        for(Long a : disciplinaDto.getAlunosMatriculadosId()) {
            Aluno aluno = new Aluno();
            
            aluno = alunoCon.listaPorId(a);
            
            alunos.add(aluno);
        }
        disciplina.setAlunosMatriculados(alunos);
        
        disciplinaCon.salva(disciplina);
        return ResponseEntity.noContent().build();
    }
}
