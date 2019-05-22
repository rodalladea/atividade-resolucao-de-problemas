/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.ChefiaDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rodrigo
 */

@Service
public class ChefiaControle {
    
    @Autowired
    private ChefiaDao chefiaDao;
    
    //As funções não precisam ser exatamente essas, mas são essas as ações tomadas pela chefia
    
    public Chefia getChefiaByProfessor(Professor professor) {
        return chefiaDao.findAll().stream()
                .filter(c -> c.getDepartamento().equalsIgnoreCase(professor.getDepartamento()))
                .findAny()
                .get();
    }
    
    public void estabelecerPlano() {};
    
    public void aprovarPlano() {};
    
    public void relatorioDirgrad() {};
    
}
