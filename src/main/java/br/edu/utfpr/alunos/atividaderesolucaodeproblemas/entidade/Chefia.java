/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
public class Chefia extends Pessoa implements Serializable{
    
    private String departamento;

    //codigo comentado
//    
//    @OneToMany
//    private List<Requerimento> avaliacaoReposicaoAula;
//    
//    @OneToMany
//    private List<Relatorio> ausenciaProfessores;
}
