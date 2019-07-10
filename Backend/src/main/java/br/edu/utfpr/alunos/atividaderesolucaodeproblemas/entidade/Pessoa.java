/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author rodrigo
 */

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Pessoa implements Serializable{
    
    @Id @GeneratedValue
    protected Long id;
    protected String nome;
    
}
