/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.ProfessorDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rodrigo
 */

public class ProfessorControle extends CrudTemplate<Professor> {
    
    @Autowired
    private ProfessorDao professorDao;
    
    
    @Override
    public void salva(Professor entidade) {
        professorDao.save(entidade);
    }

    @Override
    public void exclui(Professor entidade) {
        professorDao.delete(entidade);
    }

    @Override
    public List<Professor> listaTodos() {
        return professorDao.findAll();
    }
    
    public Professor getProfessorById(Professor professor) {
        return professorDao.findById(professor.getId()).get();
    }
    
    public List<Professor> listaProfessoresFaltantes() {
        return professorDao.findAll().stream()
                .filter(p -> p.getFaltas() > 0)
                .collect(Collectors.toList());
    }   
}
