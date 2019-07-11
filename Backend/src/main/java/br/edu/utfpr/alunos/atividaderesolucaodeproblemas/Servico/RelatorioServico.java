/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.ChefiaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.DirgradControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.ProfessorControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.RelatorioControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.RelatorioDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Dirgrad;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Relatorio;
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
public class RelatorioServico {
    private final RelatorioControle relatorioCon;
    private final ProfessorControle professorCon;
    private final ChefiaControle chefiaCon;
    private final DirgradControle dirgradCon;
    

    @Autowired
    public RelatorioServico(RelatorioControle relatorioCon, ProfessorControle professorCon,
                            ChefiaControle chefiaCon, DirgradControle dirgradCon) {
        this.relatorioCon = relatorioCon;
        this.professorCon = professorCon;
        this.chefiaCon = chefiaCon;
        this.dirgradCon = dirgradCon;
    }
    
    @GetMapping ("/servico/relatorio")
    public ResponseEntity<List<Relatorio>> listar() {
        return ResponseEntity.ok(relatorioCon.listaTodos());
    }
    
    @GetMapping ("/servico/relatorio/{id}")
    public ResponseEntity<Relatorio> listarPorId(@PathVariable Long id) {
        Relatorio relatorio = relatorioCon.listaPorId(id);
        if (relatorio != null) {
            return ResponseEntity.ok(relatorioCon.listaPorId(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping ("/servico/relatorio/naorespondido")
    public ResponseEntity<List<Relatorio>> listarNaoRespondido() {
        List<Relatorio> relatorios = relatorioCon.getNaoRespondido();
        if (relatorios != null) {
            return ResponseEntity.ok(relatorios);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping ("/servico/relatorio")
    public ResponseEntity<Relatorio> criar(@RequestBody RelatorioDTO relatorioDto) {
        Relatorio relatorio = new Relatorio();
        
        List<Professor> professores = new ArrayList<>();
        for(Long p : relatorioDto.getProfessoresId()) {
            Professor professor = new Professor();
            
            professor = professorCon.listaPorId(p);
            
            professores.add(professor);
        }
        relatorio.setProfessores(professores);
        
        Chefia chefia = chefiaCon.listaPorId(relatorioDto.getChefiaId());
        relatorio.setChefia(chefia);
        
        Dirgrad dirgrad = dirgradCon.listaPorId(relatorioDto.getDirgradId());
        relatorio.setDirgrad(dirgrad);
        
        relatorio.setObservacao(relatorioDto.getObservacao());
        relatorio.setProvidencia(relatorioDto.getProvidencia());
        
        relatorioCon.salva(relatorio);
        return ResponseEntity.status(201).body(relatorio);
    }
    
    @DeleteMapping ("/servico/relatorio/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        Relatorio relatorio = relatorioCon.listaPorId(id);
        if (relatorio != null) {
            relatorioCon.exclui(relatorio);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping ("/servico/relatorio/{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody RelatorioDTO relatorioDto) {
        Relatorio r = relatorioCon.listaPorId(id);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }
        
        Relatorio relatorio = new Relatorio();
        
        List<Professor> professores = new ArrayList<>();
        for(Long p : relatorioDto.getProfessoresId()) {
            Professor professor = new Professor();
            
            professor = professorCon.listaPorId(p);
            
            professores.add(professor);
        }
        relatorio.setProfessores(professores);
        
        Chefia chefia = chefiaCon.listaPorId(relatorioDto.getChefiaId());
        relatorio.setChefia(chefia);
        
        Dirgrad dirgrad = dirgradCon.listaPorId(relatorioDto.getDirgradId());
        relatorio.setDirgrad(dirgrad);
        
        relatorio.setObservacao(relatorioDto.getObservacao());
        relatorio.setProvidencia(relatorioDto.getProvidencia());
        
        relatorioCon.salva(relatorio);
        return ResponseEntity.noContent().build();
    }
}
