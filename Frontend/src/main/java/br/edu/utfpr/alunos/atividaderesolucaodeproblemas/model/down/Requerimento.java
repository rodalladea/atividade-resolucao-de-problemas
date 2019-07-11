/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.model.down;

import java.io.Serializable;
import java.security.Timestamp;
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
public class Requerimento {
    private Long id;
    private Date dataInicio;
    private Date dataFim;
    private Professor professor;
    private Chefia chefia;
    private Disciplina disciplina;
    //As aulas que ira deixar ou deixou de lecionar
    private List<Aula> aulasFaltantes;
    private List<Aula> aulasReposicao;
    private List<Aluno> listaAnuencia;
    //Tipo de requerimento é reposição de aula
    private Tipo tipo;
    private Status status;
    private Falta falta;
    //porcentagem de alunos do total que concordaram com a reposição
    private double porcentagemAnuencia;
    private boolean aprovado;
}
