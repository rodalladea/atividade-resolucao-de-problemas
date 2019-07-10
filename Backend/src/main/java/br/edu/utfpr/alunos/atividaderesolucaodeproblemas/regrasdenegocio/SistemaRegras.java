/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.regrasdenegocio;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.Factory;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Estados;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.excecoes.ExceptionMaxDia;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author rodrigo
 */
public class SistemaRegras {
    
    //É do sistema essas funções porque o sistema fica verificando isso
    
    public void verificaFaltaProfessor() {
        List<Aula> aulas = Factory.aulaControle.getAulasEsquecidas();
        Date agora = new Date();
        
        aulas.stream().forEach(a -> {
            int dias = (int) (((((agora.getTime() - a.getDataHora().getTime())/1000)/60)/60)/24);
            
            if (dias > 3 && a.getEstado().equals(Estados.NAO_FEITA)) {
                Professor professor = Factory.professorControle.listaPorId(a.getProfessor().getId());
                professor.setFaltas(professor.getFaltas() + 1);
                
                Factory.professorControle.salva(professor);
            }
        });
    }
    
    public void verificaAulasNaoFeitas() {
        List<Aula> aulas = Factory.aulaControle.listaTodos();
        Date agora = new Date();
        
        aulas.stream().forEach(a -> {
            if (agora.after(a.getDataHora()) && a.getEstado().equals(Estados.EM_ABERTO)) {
                a.setEstado(Estados.NAO_FEITA);
                try {
                    Factory.aulaControle.salva(a);
                } catch (ExceptionMaxDia ex) {
                    Logger.getLogger(SistemaRegras.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    }
    
}
