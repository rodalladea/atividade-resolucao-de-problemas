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
import org.springframework.stereotype.Component;

/**
 *
 * @author rodrigo
 */
@Component
public class AlunoControle extends CrudTemplate<Aluno> {
    
    private final AlunoDao alunoDao;
    
    @Autowired
    public AlunoControle(AlunoDao alunoDao) {
        this.alunoDao = alunoDao;
    }

    AlunoControle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salva(Aluno entidade) {
        alunoDao.save(entidade);
    }

    @Override
    public void exclui(Aluno entidade) {
        alunoDao.delete(entidade);
    }

    @Override
    public List<Aluno> listaTodos() {
        return alunoDao.findAll();
    }
    
    @Override
    public Aluno listaPorId(Long id) {
        return alunoDao.findById(id).get();
    }
    
}
