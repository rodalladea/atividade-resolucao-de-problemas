/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;


import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.ChefiaDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Dirgrad;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Requerimento;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Status;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Tipo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rodrigo
 */

public class ChefiaControle extends CrudTemplate<Chefia> {

    
    @Autowired
    private ChefiaDao chefiaDao;
    
    
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
}
