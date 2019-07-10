/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.Servico;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.DirgradControle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Dirgrad;
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
public class DirgradServico {
    private final DirgradControle dirgradCon;
    
    @Autowired
    public DirgradServico(DirgradControle dirgradCon) {
        this.dirgradCon = dirgradCon;
    }
    
    @GetMapping ("/servico/dirgrad")
    public ResponseEntity<List<Dirgrad>> listar() {
        return ResponseEntity.ok(dirgradCon.listaTodos());
    }
    
    @GetMapping ("/servico/dirgrad/{id}")
    public ResponseEntity<Dirgrad> listarPorId(@PathVariable Long id) {
        Dirgrad dirgrad = dirgradCon.listaPorId(id);
        if (dirgrad != null) {
            return ResponseEntity.ok(dirgradCon.listaPorId(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping ("/servico/dirgrad")
    public ResponseEntity<Dirgrad> criar(@RequestBody Dirgrad dirgrad) {
        dirgradCon.salva(dirgrad);
        return ResponseEntity.status(201).body(dirgrad);
    }
    
    @DeleteMapping ("/servico/dirgrad/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        Dirgrad dirgrad = dirgradCon.listaPorId(id);
        if (dirgrad != null) {
            dirgradCon.exclui(dirgrad);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping ("/servico/dirgrad/{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody Dirgrad dirgrad) {
        Dirgrad d = dirgradCon.listaPorId(id);
        if (d == null) {
            return ResponseEntity.notFound().build();
        }
        
        dirgradCon.salva(dirgrad);
        return ResponseEntity.noContent().build();
    }
}
