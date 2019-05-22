/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import java.util.Date;
import java.util.List;


/**
 *
 * @author rodrigo
 */
public class SistemaControle {
    private final AulaControle aulaControle = new AulaControle();
    private final ProfessorControle professorControle = new ProfessorControle();
    
    public void verificaFaltaProfessor() {
        List<Aula> aulas = aulaControle.getAulasEsquecidas();
        Date agora = new Date();
        
        aulas.stream().forEach(a -> {
            int dias = (int) (((((agora.getTime() - a.getDataHora().getTime())/1000)/60)/60)/24);
            
            if (dias > 3) {
                Professor professor = professorControle.getProfessorById(a.getProfessor());
                professor.setFaltas(professor.getFaltas() + 1);
                
                professorControle.atualiza(professor);
            }
        });
    }    
    
}
