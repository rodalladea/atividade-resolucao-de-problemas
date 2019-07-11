/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.ChefiaControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.DirgradControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.ProfessorControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.RequerimentoControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.AvaliarDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.ChefiaDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.EstabelecerPlanoDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto.ProfessorDTO;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Dirgrad;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Requerimento;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.regrasdenegocio.ChefiaRegras;
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
public class ChefiaServico {
    private final ChefiaControle chefiaCon;
    private final ChefiaRegras chefiaReg;
    private final ProfessorControle professorCon;
    private final RequerimentoControle requerimentoCon;
    private final DirgradControle dirgradCon;
    
    @Autowired
    public ChefiaServico(ChefiaControle chefiaCon, ChefiaRegras chefiaReg,
                            ProfessorControle professorCon, RequerimentoControle requerimentoCon,
                            DirgradControle dirgradCon) {
        this.chefiaCon = chefiaCon;
        this.chefiaReg = chefiaReg;
        this.professorCon = professorCon;
        this.requerimentoCon = requerimentoCon;
        this.dirgradCon = dirgradCon;
    }
    
    @GetMapping ("/servico/chefia")
    public ResponseEntity<List<Chefia>> listar() {
        return ResponseEntity.ok(chefiaCon.listaTodos());
    }
    
    @GetMapping ("/servico/chefia/professor")
    public ResponseEntity<Chefia> listarPorProfessor(@RequestBody ProfessorDTO professorDto) {
        Professor professor = professorCon.listaPorId(professorDto.getId());
        
        Chefia chefia = chefiaCon.getChefiaByProfessor(professor);
        if (chefia != null) {
            return ResponseEntity.ok(chefia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping ("/servico/chefia/{id}")
    public ResponseEntity<Chefia> listarPorId(@PathVariable Long id) {
        Chefia chefia = chefiaCon.listaPorId(id);
        if (chefia != null) {
            return ResponseEntity.ok(chefiaCon.listaPorId(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping ("/servico/chefia")
    public ResponseEntity<Chefia> criar(@RequestBody ChefiaDTO chefiaDto) {
        Chefia chefia = new Chefia();
        
        chefia.setNome(chefiaDto.getNome());
        chefia.setDepartamento(chefiaDto.getDepartamento());
        
        chefiaCon.salva(chefia);
        return ResponseEntity.status(201).body(chefia);
    }
    
    @DeleteMapping ("/servico/chefia/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        Chefia chefia = chefiaCon.listaPorId(id);
        if (chefia != null) {
            chefiaCon.exclui(chefia);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping ("/servico/chefia/{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody ChefiaDTO chefiaDto) {
        Chefia c = chefiaCon.listaPorId(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        
        Chefia chefia = new Chefia();
        
        chefia.setNome(chefiaDto.getNome());
        chefia.setDepartamento(chefiaDto.getDepartamento());
        
        chefiaCon.salva(chefia);
        return ResponseEntity.noContent().build();
    }
    
    
    @GetMapping ("/servico/chefia/estabelecerplano")
    public ResponseEntity estabelecerPlano(@RequestBody EstabelecerPlanoDTO epDto) throws ParseException {
        Professor professor = professorCon.listaPorId(epDto.getProfessorId());
        Requerimento requerimento = requerimentoCon.listaPorId(epDto.getRequerimentoId());
        
        if (professor == null || requerimento == null) {
            return ResponseEntity.notFound().build();
        }
        
        if (chefiaReg.estabelecerPlano(professor, requerimento)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(500).build();
        }
    }
    
    @GetMapping ("/servico/chefia/avaliar{idRequerimento}")
    public ResponseEntity avaliar(@PathVariable Long idRequerimento) throws ParseException {
        Requerimento requerimento = requerimentoCon.listaPorId(idRequerimento);
        
        if (requerimento == null) {
            return ResponseEntity.notFound().build();
        }
        
        if (chefiaReg.avaliar(requerimento)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(500).build();
        }
    }
    
    @GetMapping ("/servico/chefia/avaliar")
    public ResponseEntity relatorioDirgrad(@RequestBody AvaliarDTO avaliarDto) throws ParseException {
        Dirgrad dirgrad = dirgradCon.listaPorId(avaliarDto.getDirgradId());
        Chefia chefia = chefiaCon.listaPorId(avaliarDto.getChefiaId());
        
        if (chefia == null || dirgrad == null) {
            return ResponseEntity.notFound().build();
        }
        
        if (chefiaReg.relatorioDirgrad(dirgrad, chefia)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(500).build();
        }
    }
}
