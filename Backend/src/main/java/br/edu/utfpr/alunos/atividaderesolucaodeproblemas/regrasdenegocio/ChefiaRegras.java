/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.regrasdenegocio;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.Factory;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Dirgrad;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Requerimento;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Status;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Tipo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class ChefiaRegras {
    // Método chamado pelo professor que solicita um plano
    public void estabelecerPlano(Professor professor, Requerimento requerimento) throws ParseException{
        ProfessorRegras profRegras = new ProfessorRegras();
        
        if(requerimento.getTipo().equals(Tipo.MENOR_15)) {
            avaliar(requerimento);
            
        } else {
            Factory.requerimentoControle.exclui(requerimento);
            
            List<Aula> aulasReposicao = new ArrayList<Aula>();
            
            //fazer esse metodo no sistemaControle
            profRegras.geraPlanoAula(requerimento.getAulasFaltantes(), aulasReposicao, professor); 
            
            requerimento.setAulasReposicao(aulasReposicao);
            requerimento.setStatus(Status.ESPERANDO_ANUENCIA);
            
            Factory.requerimentoControle.salva(requerimento);
        }

    }

    public void avaliar(Requerimento requerimento){
        boolean aprovacao = true; //dado recebido do usuario
        
        if (aprovacao) {
            requerimento.setAprovado(true);
            Factory.aulaControle.salvaTodas(requerimento.getAulasReposicao());
        } else {
            requerimento.setAprovado(false);
        }
    }
    
    public void relatorioDirgrad(Dirgrad dirgrad, Chefia chefia) {
        List<Professor> professoresFaltantes = Factory.professorControle.listaProfessoresFaltantes();
        
        Factory.relatorioControle.salva(professoresFaltantes, dirgrad, chefia);
    }  
}
