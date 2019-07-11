/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.up;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Estados;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Formas;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Tipo;
import java.util.Date;
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
public class AulaDTO {
    private Long id;
    private Date dataHora;
    private List<Long> alunosPresenteId;
    private Long disciplinaId;
    private Formas forma;
    private Estados estado;
    private Tipo tipo;
    private Long professorId;
}
