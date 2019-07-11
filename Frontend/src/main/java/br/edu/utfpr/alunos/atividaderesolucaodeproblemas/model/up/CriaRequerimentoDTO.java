/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.up;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Falta;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down.Professor;
import java.util.Date;
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
public class CriaRequerimentoDTO {
    private Date dataInicio;
    private Date dataFim;
    private Professor professor;
    private Disciplina disciplina;
    private Falta falta;
}
