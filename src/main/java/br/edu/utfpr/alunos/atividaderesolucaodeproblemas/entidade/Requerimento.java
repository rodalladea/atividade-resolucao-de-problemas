/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade;

import java.io.Serializable;
import java.security.Timestamp; //importacoes nao utilizadas
import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Requerimento implements Serializable {
    
    @Id @GeneratedValue
    private Long id;
    
    @Temporal (TemporalType.DATE)
    private Date dataInicio;
    
    @Temporal (TemporalType.DATE)
    private Date dataFim;
    
    @ManyToOne
    private Professor professor;
    
    @ManyToOne
    private Chefia chefia;
    
    @ManyToOne
    private Disciplina disciplina;
    
    //As aulas que ira deixar ou deixou de lecionar
    @ElementCollection
    private List<Aula> aulasFaltantes;
    
    @ElementCollection
    private List<Aula> aulasReposicao;
    
    @ElementCollection
    private List<Aluno> listaAnuencia;
    
    //Tipo de requerimento é reposição de aula
    @Enumerated (EnumType.STRING)
    private Tipo tipo;
    
    @Enumerated (EnumType.STRING)
    private Status status;
    
    @Enumerated (EnumType.STRING)
    private Falta falta;
    
    //porcentagem de alunos do total que concordaram com a reposição
    private double porcentagemAnuencia;
    private boolean aprovado;
}
