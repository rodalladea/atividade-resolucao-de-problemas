/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dto;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Falta;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Status;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Tipo;
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
public class RequerimentoDTO {
    private Long id;
    private Date dataInicio;
    private Date dataFim;
    private Long professorId;
    private Long chefiaId;
    private Long disciplinaId;
    //As aulas que ira deixar ou deixou de lecionar
    private List<Long> aulasFaltantesId;
    private List<Long> aulasReposicaoId;
    private List<Long> listaAnuenciaId;
    //Tipo de requerimento é reposição de aula
    private Tipo tipo;
    private Status status;
    private Falta falta;
}
