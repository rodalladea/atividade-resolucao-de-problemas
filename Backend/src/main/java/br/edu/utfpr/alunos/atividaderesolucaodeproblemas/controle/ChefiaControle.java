/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;


import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.ChefiaDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rodrigo
 */

public class ChefiaControle extends CrudTemplate<Chefia> {


    private ChefiaDao chefiaDao;
    
    @Autowired
    public ChefiaControle(ChefiaDao chefiaDao) {
        this.chefiaDao = chefiaDao;
    }

    ChefiaControle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public void salva(Chefia entidade) {
        chefiaDao.save(entidade);
    }

    @Override
    public void exclui(Chefia entidade) {
        chefiaDao.delete(entidade);
    }

    @Override
    public List<Chefia> listaTodos() {
        return chefiaDao.findAll();
    }
    
    public Chefia getChefiaByProfessor(Professor professor) {
        return chefiaDao.findAll().stream()
                .filter(c -> c.getDepartamento().equalsIgnoreCase(professor.getDepartamento()))
                .findAny()
                .get();
    }

    @Override
    public Chefia listaPorId(Long id) {
        return chefiaDao.findById(id).get();
    }    
}
