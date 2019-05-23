/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.SemestreAcademico;
import org.springframework.beans.factory.annotation.Autowired;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.SemestreAcademicoDao;
import java.util.List;

/**
 *
 * @author rodrigo
 */

public class SemestreAcademicoControle extends CrudTemplate<SemestreAcademico> {
    
    @Autowired
    private SemestreAcademicoDao semestreAcademicoDao;
    
    @Override
    public void salva(SemestreAcademico entidade) { //poderia ser um método protected
        semestreAcademicoDao.save(entidade);
    }
    
    @Override
    public void exclui(SemestreAcademico entidade) { //poderia ser um método protected
        semestreAcademicoDao.delete(entidade);
    }
    
    @Override
    public void atualiza(SemestreAcademico entidade) {
        this.exclui(entidade);
        this.salva(entidade);
    }

    @Override
    public List<SemestreAcademico> listaTodos() {
        return semestreAcademicoDao.findAll();
    }
    
    public SemestreAcademico getSemestreAcademicoById(SemestreAcademico semestreAcademico) {
        return semestreAcademicoDao.findById(semestreAcademico.getId()).get(); //antes do get teria que verificar se existe com o isPresent()
    }
}
