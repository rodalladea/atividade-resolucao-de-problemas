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
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Formas; //importacao nao utilizada
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Requerimento;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Status;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Tipo;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rodrigo
 */

public class RequerimentoControle extends CrudTemplate<Requerimento> {
    
    @Autowired
    private RequerimentoDao requerimentoDao;
    
    @Override
    public void salva(Requerimento entidade) {
        requerimentoDao.save(entidade);
    }
    
    @Override
    public void exclui(Requerimento entidade) {
        requerimentoDao.delete(entidade);
    }
    
    @Override
    public void atualiza(Requerimento entidade) {
        this.exclui(requerimentoDao.findById(entidade.getId()).get()); //antes do get teria que verificar se existe com o isPresent()
        this.salva(entidade);
    }

    @Override
    public List<Requerimento> listaTodos() { //poderia ser um método protected
        return requerimentoDao.findAll();
    }
    
    public void salva(Date dataInicio, //método tem muitos parametros (13 no total)
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
        List<Requerimento> listaRequerimentos = this.listaTodos().stream() //ao inves de salvar essa expressao na lista e retornar a lista, poderia retornar a expressao direto
                .filter(r -> (r.getTipo().equals(Tipo.MAIOR_15) &&
                        r.getProfessor().equals(professor)) &&
                        (r.getStatus().equals(Status.ESPERANDO_ANUENCIA) ||
                         !r.isAprovado()))
                .collect(Collectors.toList());
        
        return listaRequerimentos;
    }
    
    //Requerimentos que não foram finalizados precisam ser completos tanto para inserção de aulas quanto anuencia
    public List<Requerimento> requerimentosIncompleto(Professor professor, Disciplina disciplina) {
        List<Requerimento> listaRequerimentos = this.listaTodos().stream() //ao inves de salvar a expressao na lista e retornar a lista, poderia ter retornado a expressao direto
                .filter(r -> r.getStatus().equals(Status.INCOMPLETO) &&
                        r.getProfessor().equals(professor))
                .collect(Collectors.toList());
        
        return listaRequerimentos;
    }

    
}
