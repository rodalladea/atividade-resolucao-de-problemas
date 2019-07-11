/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.RelatorioDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Dirgrad;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Relatorio;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author rodrigo
 */
@Component
public class RelatorioControle extends CrudTemplate<Relatorio> {
    
    private final RelatorioDao relatorioDao;
    
    @Autowired
    public RelatorioControle(RelatorioDao relatorioDao) {
        this.relatorioDao = relatorioDao;
    }

    RelatorioControle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void salva(List<Professor> professores, Dirgrad dirgrad, Chefia chefia) {
        Relatorio relatorio = Relatorio.builder().professores(professores).dirgrad(dirgrad).chefia(chefia)
                .observacao(null).providencia(null).build();
        
        relatorioDao.save(relatorio);
    }
    
    @Override
    public void salva(Relatorio relatorio) {
        relatorioDao.save(relatorio);
    }
    
    @Override
    public void exclui(Relatorio entidade) {
        relatorioDao.delete(entidade);
    }

    @Override
    public List<Relatorio> listaTodos() {
        return relatorioDao.findAll();
    }
    
    @Override
    public Relatorio listaPorId(Long id) {
        return relatorioDao.findById(id).get();
    }
    
    public List<Relatorio> getNaoRespondido() {
        return relatorioDao.findAll().stream()
                .filter(r -> r.getProvidencia().trim().isEmpty())
                .collect(Collectors.toList());
    }
}
