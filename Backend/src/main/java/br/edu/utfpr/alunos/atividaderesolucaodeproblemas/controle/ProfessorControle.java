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
import org.springframework.stereotype.Component;

/**
 *
 * @author rodrigo
 */
@Component
public class ProfessorControle extends CrudTemplate<Professor> {
    
    private final ProfessorDao professorDao;
    
    @Autowired
    public ProfessorControle(ProfessorDao professorDao) {
        this.professorDao = professorDao;
    }

    ProfessorControle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
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
    
    @Override
    public Professor listaPorId(Long id) {
        return professorDao.findById(id).get();
    }
    
    public List<Professor> listaProfessoresFaltantes() {
        return professorDao.findAll().stream()
                .filter(p -> p.getFaltas() > 0)
                .collect(Collectors.toList());
    }   
}
