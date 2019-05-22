/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.AlunoDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aluno;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rodrigo
 */

@Service
public class AlunoControle extends CrudTemplate<Aluno> {
    
    @Autowired
    private AlunoDao alunoDao;
    
    @Override
    protected void salva(Aluno entidade) {
        alunoDao.save(entidade);
    }

    @Override
    protected void exclui(Aluno entidade) {
        alunoDao.delete(entidade);
    }

    @Override
    protected void atualiza(Aluno entidade) {
        this.exclui(entidade);
        
        this.salva(entidade);
    }

    @Override
    protected List<Aluno> listaTodos() {
        return alunoDao.findAll();
    }
    
}
