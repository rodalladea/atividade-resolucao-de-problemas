/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.DirgradDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Dirgrad;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Relatorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author rodrigo
 */

public class DirgradControle extends CrudTemplate<Dirgrad> {
    
    @Autowired
    private DirgradDao dirgradDao;
    
    
    @Override
    protected void salva(Dirgrad entidade) {
        dirgradDao.save(entidade);
    }

    @Override
    protected void exclui(Dirgrad entidade) {
        dirgradDao.delete(entidade);
    }

    @Override
    protected void atualiza(Dirgrad entidade) {
        this.exclui(entidade);
        this.salva(entidade);
    }

    @Override
    protected List<Dirgrad> listaTodos() {
        return dirgradDao.findAll();
    }

    public void registraProvidencia(Relatorio relatorio, String observacao, String providencia) {
        relatorio.setObservacao(observacao);
        relatorio.setProvidencia(providencia);
        
        Factory.relatorioControle.salva(relatorio);
    }
}
