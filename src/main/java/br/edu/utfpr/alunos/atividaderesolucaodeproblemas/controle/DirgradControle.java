/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Relatorio;

import org.springframework.stereotype.Service;

/**
 *
 * @author rodrigo
 */

@Service
public class DirgradControle {

    private final RelatorioControle relatorioControle = new RelatorioControle();

    public void registraProvidencia(Relatorio relatorio, String observacao, String providencia) {
        relatorio.setObservacao(observacao);
        relatorio.setProvidencia(providencia);
        
        relatorioControle.salva(relatorio);
    }
}
