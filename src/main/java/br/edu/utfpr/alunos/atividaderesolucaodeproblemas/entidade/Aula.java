/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class Aula implements Serializable {
    
    @Id @GeneratedValue
    private Long id;
    
    //Tem que arrumar para conseguir colocar data e hora    
    @Temporal(TemporalType.DATE)
    private Date dataHora;
    
    @ManyToMany (mappedBy = "aulas")
    private List<Aluno> alunosPresente;
    
    @ManyToOne
    private Disciplina disciplina;
    
    @Enumerated (EnumType.STRING)
    private Formas forma;
    
    private String conteudo;
    
    @Enumerated (EnumType.STRING)
    private Estados estado;
    
    @Enumerated (EnumType.STRING)
    private Tipo tipo;
    
}
