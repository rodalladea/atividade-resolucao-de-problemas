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
public abstract class CrudTemplate<T> {
    
    protected abstract void salva(T entidade) throws Exception;
    protected abstract void exclui(T entidade);
    protected abstract List<T> listaTodos();
    protected abstract T listaPorId(Long id);
}
