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

/**
 *
 * @author rodrigo
 */

public class RelatorioControle extends CrudTemplate<Relatorio> {
    
    @Autowired
    private RelatorioDao relatorioDao;
    
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
    protected void exclui(Relatorio entidade) {
        relatorioDao.delete(entidade);
    }

    @Override
    protected void atualiza(Relatorio entidade) {
        this.exclui(entidade);
        this.salva(entidade);
    }

    @Override
    protected List<Relatorio> listaTodos() {
        return relatorioDao.findAll();
    }
    
    public List<Relatorio> getNaoRespondido() {
        return relatorioDao.findAll().stream()
                .filter(r -> r.getProvidencia().trim().isEmpty())
                .collect(Collectors.toList());
    }
}
