/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade;

import java.io.Serializable;
import javax.persistence.Entity;
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
@Entity
public class Professor extends Pessoa implements Serializable {
    
    private int faltas;
    private String departamento;
    
//    @OneToMany
//    private List<Requerimento> requesicaoReposicaoAula;
}
