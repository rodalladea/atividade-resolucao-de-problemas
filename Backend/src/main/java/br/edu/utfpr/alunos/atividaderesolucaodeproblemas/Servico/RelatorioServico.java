/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.RelatorioControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Relatorio;
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
    
    @Autowired
    public RelatorioServico(RelatorioControle relatorioCon) {
        this.relatorioCon = relatorioCon;
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
    public ResponseEntity<Relatorio> criar(@RequestBody Relatorio relatorio) {
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
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody Relatorio relatorio) {
        Relatorio r = relatorioCon.listaPorId(id);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }
        
        relatorioCon.salva(relatorio);
        return ResponseEntity.noContent().build();
    }
}
