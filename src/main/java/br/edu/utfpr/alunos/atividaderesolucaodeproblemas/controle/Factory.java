/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

/**
 *
 * @author rodrigo
 */
public class Factory {
    public static final AulaControle aulaControle = new AulaControle();
    public static final RequerimentoControle requerimentoControle = new RequerimentoControle();
    public static final ChefiaControle chefiaControle = new ChefiaControle();
    public static final AlunoControle alunoControle = new AlunoControle();
    public static final RelatorioControle relatorioControle = new RelatorioControle();
    public static final ProfessorControle professorControle = new ProfessorControle();
    public static final SemestreAcademicoControle semestreAcademicoControle = new SemestreAcademicoControle();
}
