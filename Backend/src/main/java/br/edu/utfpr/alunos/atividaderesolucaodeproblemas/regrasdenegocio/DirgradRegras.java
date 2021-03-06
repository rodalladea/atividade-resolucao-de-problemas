/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.regrasdenegocio;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.Factory;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Relatorio;
import org.springframework.stereotype.Component;

/**
 *
 * @author rodrigo
 */
@Component
public class DirgradRegras {
    
    public boolean registraProvidencia(Relatorio relatorio, String observacao, String providencia) {
        relatorio.setObservacao(observacao);
        relatorio.setProvidencia(providencia);
        
        Factory.relatorioControle.salva(relatorio);
        
        return true;
    }
}
