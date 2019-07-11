/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.up;

import java.util.List;
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
public class RelatorioDTO {
    private Long id;
    private List<Long> professoresId;
    private Long chefiaId;
    private Long dirgradId;
    private String observacao;
    private String providencia;
}
