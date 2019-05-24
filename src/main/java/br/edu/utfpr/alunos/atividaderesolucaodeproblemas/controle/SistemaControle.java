/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Estados;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import java.util.Date;
import java.util.List;


/**
 *
 * @author rodrigo
 */
public class SistemaControle { //poderia ser uma classe privada

    //não era necessária a criação dessa classe
    
    public void verificaFaltaProfessor() { //esse método poderia estar dentro de ProfessorControle
        List<Aula> aulas = Factory.aulaControle.getAulasEsquecidas();
        Date agora = new Date();
        
        aulas.stream().forEach(a -> { //poderia usar somente o forEach sem o stream()
            int dias = (int) (((((agora.getTime() - a.getDataHora().getTime())/1000)/60)/60)/24);
            
            if (dias > 3 && a.getEstado().equals(Estados.NAO_FEITA)) {
                Professor professor = Factory.professorControle.getProfessorById(a.getProfessor());
                professor.setFaltas(professor.getFaltas() + 1);
                
                Factory.professorControle.atualiza(professor);
            }
        });
    }
    
    public void verificaAulasNaoFeitas() { //esse método deveria estar dentro de AulaControle
        List<Aula> aulas = Factory.aulaControle.listaTodos();
        Date agora = new Date();
        
        aulas.stream().forEach(a -> { //poderia usar somente o forEach sem o stream()
            if (agora.after(a.getDataHora()) && a.getEstado().equals(Estados.EM_ABERTO)) {
                a.setEstado(Estados.NAO_FEITA);
                Factory.aulaControle.atualiza(a);
            }
            
        });
    }
    
}
