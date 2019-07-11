/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Relatorio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author rodrigo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirgradProvidenciaDTO {
    private Relatorio relatorio;
    private String observacao;
    private String providencia;
}
