/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import java.util.List;

/**
 *
 * @author rodrigo
 */

//nao havia necessidade de usar o template method se o JPARepository esta sendo usado, porque ele ja fornece esses métodos prontos
public abstract class CrudTemplate<T> {
    
    protected abstract void salva(T entidade) throws Exception; //poderia colocar uma excecao específica ao invés de uma geral
    protected abstract void exclui(T entidade);
    protected abstract void atualiza(T entidade);
    protected abstract List<T> listaTodos();
}
