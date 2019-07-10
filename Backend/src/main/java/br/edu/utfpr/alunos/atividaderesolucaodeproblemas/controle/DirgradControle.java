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
    
    private DirgradDao dirgradDao;
    
    @Autowired
    public DirgradControle(DirgradDao dirgradDao) {
        this.dirgradDao = dirgradDao;
    }
    
    @Override
    public void salva(Dirgrad entidade) {
        dirgradDao.save(entidade);
    }

    @Override
    public void exclui(Dirgrad entidade) {
        dirgradDao.delete(entidade);
    }

    @Override
    public List<Dirgrad> listaTodos() {
        return dirgradDao.findAll();
    }
    
    @Override
    public Dirgrad listaPorId(Long id) {
        return dirgradDao.findById(id).get();
    }
}
