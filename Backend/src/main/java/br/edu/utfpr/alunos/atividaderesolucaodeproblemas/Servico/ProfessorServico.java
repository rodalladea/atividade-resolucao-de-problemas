/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.ProfessorControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.CriaRequerimentoDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.ProfessorDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Falta;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.regrasdenegocio.ProfessorRegras;
import java.text.ParseException;
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
    private final ProfessorRegras professorReg;
    
    @Autowired
    public ProfessorServico(ProfessorControle professorCon, ProfessorRegras professorReg) {
        this.professorCon = professorCon;
        this.professorReg = professorReg;
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
    public ResponseEntity<Professor> criar(@RequestBody ProfessorDTO professorDto) {
        Professor professor = new Professor();
        
        professor.setNome(professorDto.getNome());
        professor.setFaltas(professorDto.getFaltas());
        professor.setDepartamento(professorDto.getDepartamento());
        
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
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody ProfessorDTO professorDto) {
        Professor p = professorCon.listaPorId(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        
        Professor professor = new Professor();
        
        professor.setNome(professorDto.getNome());
        professor.setFaltas(professorDto.getFaltas());
        professor.setDepartamento(professorDto.getDepartamento());
        
        professorCon.salva(professor);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping ("/servico/dirgrad/registraprovidencia")
    public ResponseEntity registraProvidencia(@RequestBody CriaRequerimentoDTO provDto) throws ParseException {
        
        if(professorReg.criaRequerimento(provDto.getDataInicio(),
                provDto.getDataFim(),
                provDto.getProfessor(),
                provDto.getDisciplina(),
                provDto.getFalta())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(500).build();
        }
    }
}
