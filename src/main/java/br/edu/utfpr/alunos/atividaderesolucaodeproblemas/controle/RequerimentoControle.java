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
public class RequerimentoControle {
    
    @Autowired
    private RequerimentoDao requerimentoDao;
    
    public void salva(Date dataInicio,
                    Date dataFim,
                    Professor professor,
                    Chefia chefia,
                    Disciplina disciplina,
                    List<Aula> aulasFaltantes,
                    List<Aula> aulasReposicao,
                    List<Aluno> listaAnuencia,
                    Formas forma,
                    Tipo tipo,
                    Status status,
                    Falta falta,
                    double porcentagemAnuencia,
                    boolean aprovado) {
        requerimentoDao.save(Requerimento.builder().dataInicio(dataInicio).dataFim(dataFim).professor(professor)
                .chefia(chefia).aulasFaltantes(aulasFaltantes).aulasReposicao(aulasReposicao)
                .listaAnuencia(listaAnuencia).forma(forma).tipo(tipo).status(status).falta(falta)
                .porcentagemAnuencia(porcentagemAnuencia).aprovado(aprovado).disciplina(disciplina).build());
    }
    
    public void salva(Requerimento requerimento) {
        requerimentoDao.save(requerimento);
    }
    
    public void exclui(Requerimento requerimento) {
        requerimentoDao.delete(requerimento);
    }
    
    //Plano realizado pelo dirgrad no requerimento
    public List<Requerimento> verificaPlanoDirgrad(Professor professor, Disciplina disciplina) {
        List<Requerimento> listaRequerimentos = requerimentoDao.findAll().stream()
                .filter(r -> r.getStatus().equals(Tipo.MAIOR_15))
                .collect(Collectors.toList());
        
        return listaRequerimentos;
    }
}
