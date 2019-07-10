/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.regrasdenegocio;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle.Factory;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aluno;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Estados;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Falta;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Formas;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Requerimento;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Status;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Tipo;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.excecoes.ExceptionMaxDia;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class ProfessorRegras {
        
    //Paramentros que serão inseridos pelo usuário professor
    public void criaRequerimento(Date dataInicio,
                                    Date dataFim,
                                    Professor professor,
                                    Disciplina disciplina,
                                    Falta falta) throws ParseException {
        
        //Descobre aulas que ira faltar em uma determinado disciplina
        List<Aula> aulasFaltantes = Factory.aulaControle.getAulasFaltantes(dataInicio, dataFim, professor, disciplina);
        //Quantidade de dias que ira faltar
        int quantDias = (int) (((((dataFim.getTime() - dataInicio.getTime())/1000)/60)/60)/24);
        //Qual é a chefia do professor
        Chefia chefia = Factory.chefiaControle.getChefiaByProfessor(professor);
        Date dataFinalSemestre = Factory.semestreAcademicoControle
                                .listaPorId(disciplina.getSemestre().getId()).getDataFinal();
        
        //Se quantidade manor que 15 dias o professor define o plano de aula para reposição
        //Caso contrario a chefia define
        if (quantDias <= 15 && falta.equals(Falta.PREVISTO) && 
                (dataFim.getTime() < 
                dataFinalSemestre.getTime())) {
            
            Factory.requerimentoControle.salva(dataInicio, dataFim, professor, chefia, disciplina,
                    aulasFaltantes, null, null, Tipo.MENOR_15, Status.INCOMPLETO, 
                    falta, 0, false);
            
        } else {
            
            Factory.requerimentoControle.salva(dataInicio, dataFim, professor, chefia, disciplina,
                    aulasFaltantes, null, null, Tipo.MAIOR_15, Status.COMPLETO, 
                    falta, 0, false);
            
        }
        
    }
    
    public void insereAulasRequerimento(Requerimento requerimento) throws ParseException {
        List<Aula> aulasReposicao = new ArrayList<>();

        //Define o plano de aulas
        geraPlanoAula(requerimento.getAulasFaltantes(), aulasReposicao, requerimento.getProfessor());
        
        requerimento.setAulasFaltantes(aulasReposicao);
        
        Factory.requerimentoControle.salva(requerimento);
    }
    
    public void realizaAnuencia(Requerimento requerimento) {
        
        List<Aluno> listaAnuencia = new ArrayList<>();
        
        //Verifica anuencia
        for (int i = 0; i < requerimento.getDisciplina().getAlunosMatriculados().size(); i ++) {
            boolean concorda = true; //Pode ser tanto true quanto false, o professor vai selecionando
            if (concorda && requerimento.getDisciplina().getAlunosMatriculados().get(i).getPorcentagemPresenca() > 75) { 
                listaAnuencia.add(requerimento.getDisciplina().getAlunosMatriculados().get(i));
            }
        }

        //Calcula porcentagem de anuencia
        double porcentagemAnuencia = (listaAnuencia.size()/
                                        requerimento.getDisciplina().getAlunosMatriculados().size()) * 100;
        
        requerimento.setListaAnuencia(listaAnuencia);
        requerimento.setPorcentagemAnuencia(porcentagemAnuencia);
        requerimento.setStatus(Status.COMPLETO);
        
        Factory.requerimentoControle.salva(requerimento);
    }
    
    public void geraPlanoAula(List<Aula> aulasFaltantes, 
                                    List<Aula> aulasReposicao, Professor professor) throws ParseException {
        //variaveis aleatorias criadas para inserção, que no caso o usuario iria inserindo
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dataAula = "25/05/2019 15:30:00";
        String conteudo = "conteudo";

        //Define o plano de aulas
        for (int i = 0; i < aulasFaltantes.size(); i++) {
            Aula aula = Factory.aulaControle.geraAula(dateFormat.parse(dataAula),
                    null, 
                    aulasFaltantes.get(i).getDisciplina(),
                    Formas.PRESENCIAL, 
                    conteudo, 
                    Estados.EM_ABERTO, 
                    Tipo.REPOSIÇÃO, 
                    professor);

            aulasReposicao.add(aula);
        }
        
    }
    
    public void realizaAula(Aula aula) throws ExceptionMaxDia {
        
        aula.setAlunosPresente(realizaChamada(aula));
        aula.setEstado(Estados.FEITA);
        
        Factory.aulaControle.salva(aula);
    }
    
    public List<Aluno> realizaChamada(Aula aula) {
        List<Aluno> total = aula.getDisciplina().getAlunosMatriculados();
        List<Aluno> presente = new ArrayList<>();
        Aluno aluno = null;
        
        for (int i = 0; i < total.size(); i++) {
            boolean responde = true; //entrada no sistema pelo usuario entao pode ser false
            
            if (responde) {
                aluno = total.get(i);
                
                presente.add(aluno);
                
                aluno.setPorcentagemPresenca((double) (Factory.aulaControle.getAulasComAluno(aluno).size()
                                            /Factory.aulaControle.getAulasDisciplina(aula.getDisciplina()).size()) * 100);
                Factory.alunoControle.salva(aluno);
            }
        }
        
        return presente;
    }
}
