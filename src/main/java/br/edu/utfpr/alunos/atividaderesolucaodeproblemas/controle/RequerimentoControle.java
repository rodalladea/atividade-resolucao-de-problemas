/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.RequerimentoDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aluno;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Falta;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Formas;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Requerimento;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Status;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Tipo;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rodrigo
 */

@Service
public class RequerimentoControle extends CrudTemplate<Requerimento> {
    
    @Autowired
    private RequerimentoDao requerimentoDao;
    
    @Override
    public void salva(Requerimento requerimento) {
        requerimentoDao.save(requerimento);
    }
    
    @Override
    public void exclui(Requerimento requerimento) {
        requerimentoDao.delete(requerimento);
    }
    
    @Override
    public void atualiza(Requerimento entidade) {
        this.exclui(requerimentoDao.findById(entidade.getId()).get());
        
        requerimentoDao.save(entidade);
    }

    @Override
    public List<Requerimento> listaTodos() {
        return requerimentoDao.findAll();
    }
    
    public void salva(Date dataInicio,
                    Date dataFim,
                    Professor professor,
                    Chefia chefia,
                    Disciplina disciplina,
                    List<Aula> aulasFaltantes,
                    List<Aula> aulasReposicao,
                    List<Aluno> listaAnuencia,
                    Tipo tipo,
                    Status status,
                    Falta falta,
                    double porcentagemAnuencia,
                    boolean aprovado) {
        requerimentoDao.save(Requerimento.builder().dataInicio(dataInicio).dataFim(dataFim).professor(professor)
                .chefia(chefia).aulasFaltantes(aulasFaltantes).aulasReposicao(aulasReposicao)
                .listaAnuencia(listaAnuencia).tipo(tipo).status(status).falta(falta)
                .porcentagemAnuencia(porcentagemAnuencia).aprovado(aprovado).disciplina(disciplina).build());
    }
    
    //Plano realizado pela chefia no requerimento precisa ser realizado anuencia
    public List<Requerimento> requerimentosChefia(Professor professor, Disciplina disciplina) {
        List<Requerimento> listaRequerimentos = this.listaTodos().stream()
                .filter(r -> (r.getTipo().equals(Tipo.MAIOR_15) &&
                        r.getProfessor().equals(professor)) &&
                        (r.getStatus().equals(Status.ESPERANDO_ANUENCIA) ||
                         !r.isAprovado()))
                .collect(Collectors.toList());
        
        return listaRequerimentos;
    }
    
    //Requerimentos que não foram finalizados precisam ser completos tanto para inserção de aulas quanto anuencia
    public List<Requerimento> requerimentosIncompleto(Professor professor, Disciplina disciplina) {
        List<Requerimento> listaRequerimentos = this.listaTodos().stream()
                .filter(r -> r.getStatus().equals(Status.INCOMPLETO) &&
                        r.getProfessor().equals(professor))
                .collect(Collectors.toList());
        
        return listaRequerimentos;
    }

    
}
