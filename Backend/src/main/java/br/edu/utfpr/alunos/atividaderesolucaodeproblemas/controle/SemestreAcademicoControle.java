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
    
    private SemestreAcademicoDao semestreAcademicoDao;
    
    @Autowired
    public SemestreAcademicoControle(SemestreAcademicoDao semestreAcademicoDao) {
        this.semestreAcademicoDao = semestreAcademicoDao;
    }

    SemestreAcademicoControle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salva(SemestreAcademico entidade) {
        semestreAcademicoDao.save(entidade);
    }
    
    @Override
    public void exclui(SemestreAcademico entidade) {
        semestreAcademicoDao.delete(entidade);
    }

    @Override
    public List<SemestreAcademico> listaTodos() {
        return semestreAcademicoDao.findAll();
    }
    
    @Override
    public SemestreAcademico listaPorId(Long id) {
        return semestreAcademicoDao.findById(id).get();
    }
}
