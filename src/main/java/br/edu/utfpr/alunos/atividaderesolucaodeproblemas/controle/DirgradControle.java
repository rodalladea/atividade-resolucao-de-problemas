/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.AulaDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.ProfessorDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Dirgrad;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Relatorio;

import javax.persistence.ElementCollection;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class DirgradControle {

    private final ProfessorDao professorDao;
    private final AulaDao aulaDao;  //talvez não precise

    public DirgradControle(ProfessorDao professorDao, AulaDao aulaDao) {
        this.professorDao = professorDao;
        this.aulaDao = aulaDao;
    }


    //As funções não precisam ser exatamente essas, mas são essas as ações tomadas pelo dirgrad

    public Relatorio registraProvidencia(List<Professor> professores,
                                         Chefia chefia,
                                         String observacao,
                                         String providencia) {
        // gerando registros de ausências não justificadas ou não compensadas até o término do semestre/ano letivo
        // se a(s) ausências não estão justificadas nem compensadas até o fim do semestre/ano letivo, armazeno
        // os dados do professor com essa pendência
//
//        if(professores.getAulasFaltantes().size() > 0){ (algo assim)
            return Relatorio.builder().professores(professores).chefia(chefia).observacao(observacao).providencia(providencia)
                    .build();
        }
//        else{
            // Compete à Diretoria de Graduação e Educação Profissional dar encaminhamento
            // aos registros recebidos das Chefias de Departamento e/ou Coordenações de Curso para as devidas
            // providências

//        return Relatorio.builder().chefia(chefia).observacao(observacao).providencia(providencia).build();
//        }
    }

}
