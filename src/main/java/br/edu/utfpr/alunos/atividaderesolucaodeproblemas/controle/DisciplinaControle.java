/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.DisciplinaDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rodrigo
 */

public class DisciplinaControle extends CrudTemplate<Disciplina> {
    
    @Autowired
    private DisciplinaDao disciplinaDao;
    
    @Override
    public void salva(Disciplina entidade) {
        disciplinaDao.save(entidade);
    }
    
    @Override
    public void exclui(Disciplina entidade) {
        disciplinaDao.delete(entidade);
    }

    @Override
    public List<Disciplina> listaTodos() {
        return disciplinaDao.findAll();
    }
}
