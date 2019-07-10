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
    
    private RequerimentoDao requerimentoDao;
    
    @Autowired
    public RequerimentoControle(RequerimentoDao requerimentoDao) {
        this.requerimentoDao = requerimentoDao;
    }

    RequerimentoControle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salva(Requerimento entidade) {
        requerimentoDao.save(entidade);
    }
    
    @Override
    public void exclui(Requerimento entidade) {
        requerimentoDao.delete(entidade);
    }

    @Override
    public List<Requerimento> listaTodos() {
        return requerimentoDao.findAll();
    }
    
    @Override
    public Requerimento listaPorId(Long id) {
        return requerimentoDao.findById(id).get();
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
