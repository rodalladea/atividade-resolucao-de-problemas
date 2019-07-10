/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.excecoes;

/**
 *
 * @author rodrigo
 */
public class ExceptionMaxDia extends Exception {
    public ExceptionMaxDia(String msg) { super("Dia já possui 6 aulas da mesma disciplina: " + msg); }
}
