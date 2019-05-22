/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.AulaDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.DisciplinaDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.RequerimentoDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aluno;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Estados;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Formas;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Requerimento;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Tipo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rodrigo
 */

@Service
public class ProfessorControle {
    
    private final AulaDao aulaDao;
    private final DisciplinaDao disciplinaDao;
    private final RequerimentoDao requerimentoDao;
    
    @Autowired
    public ProfessorControle(AulaDao aulaDao, DisciplinaDao disciplinaDao, RequerimentoDao requerimentoDao) {
        this.aulaDao = aulaDao;
        this.disciplinaDao = disciplinaDao;
        this.requerimentoDao = requerimentoDao;
    }
    
    //Paramentros que serão inseridos pelo usuário professor
    public void requisitarReposicao(Date dataInicio,
                                    Date dataFim,
                                    Professor professor,
                                    Disciplina disciplina,
                                    Chefia chefia) throws ParseException {
        
        //Descobre aulas que ira faltar em uma determinado disciplina
        List<Aula> aulasFaltantes = getAulasFaltantes(dataInicio, dataFim, professor, disciplina);
        //Quantidade de dias que ira faltar
        int quantDias = (int) (((((dataFim.getTime() - dataInicio.getTime())/1000)/60)/60)/24);
        
        //Se quantidade manor que 15 dias o professor define o plano de aula para reposição
        if (quantDias <= 15) {
            //variaveis aleatorias criadas para inserção, que no caso o usuario iria inserindo
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String dataAula = "25/05/2019 15:30:00";
            String conteudo = "conteudo";
            List<Aula> aulasReposicao = new ArrayList<>();
            List<Aluno> listaAnuencia = new ArrayList<>();
            
            //Define o plano de aulas
            for (int i = 0; i < aulasFaltantes.size(); i++) {
                Aula aula = geraAula(dateFormat.parse(dataAula),
                        null, 
                        aulasFaltantes.get(i).getDisciplina(),
                        Formas.PRESENCIAL, 
                        conteudo, 
                        Estados.EM_ABERTO, 
                        Tipo.REPOSIÇÃO, 
                        professor);
                
                aulasReposicao.add(aula);
            }
            
            //Verifica anuencia
            for (int i = 0; i < disciplina.getAlunosMatriculados().size(); i ++) {
                if (true && disciplina.getAlunosMatriculados().get(i).getPorcentagemPresenca() > 75) { //Pode ser tanto true quanto false, o professor vai selecionando
                    listaAnuencia.add(disciplina.getAlunosMatriculados().get(i));
                }
            }
            
            //Calcula porcentagem de anuencia
            double porcentagemAnuencia = (listaAnuencia.size()/disciplina.getAlunosMatriculados().size()) * 100;
            
            //Gera requerimento com o plano feito
            requerimentoDao.save(geraRequerimento(dataInicio, dataFim, professor, chefia, disciplina,
                    aulasFaltantes, aulasReposicao, listaAnuencia, Formas.PRESENCIAL, 
                    Tipo.REPOSIÇÃO, porcentagemAnuencia, false));
        } else {
            //Gera requerimento com plano para o dirgrad fazer, pois faltara mais de 15 dias
            requerimentoDao.save(geraRequerimento(dataInicio, dataFim, professor, chefia, disciplina,
                    aulasFaltantes, null, null, null, 
                    null, 0, false));
        }
        
    };
    
    //Retorna uma lista de planos feitos pelo dirgrad
    public List<Requerimento> verificaPlanoDirgrad(Professor professor, Disciplina disciplina) {
        List<Requerimento> listaRequerimentos = requerimentoDao.findAll().stream()
                .filter(r -> (r.getProfessor().equals(professor) &&
                        r.getDisciplina().equals(disciplina) &&
                        (((((r.getDataFim().getTime() - r.getDataInicio().getTime())/1000)/60)/60)/24) > 15 &&
                        !r.isAprovado()))
                .collect(Collectors.toList());
        
        return listaRequerimentos;
    }
    
    //Criar um controlador para requerimento
    public Requerimento geraRequerimento(Date dataInicio,
                                        Date dataFim,
                                        Professor professor,
                                        Chefia chefia,
                                        Disciplina disciplina,
                                        List<Aula> aulasFaltantes,
                                        List<Aula> aulasReposicao,
                                        List<Aluno> listaAnuencia,
                                        Formas forma,
                                        Tipo tipo,
                                        double porcentagemAnuencia,
                                        boolean aprovado) {
        return Requerimento.builder().dataInicio(dataInicio).dataFim(dataFim).professor(professor)
                .chefia(chefia).aulasFaltantes(aulasFaltantes).aulasReposicao(aulasReposicao)
                .listaAnuencia(listaAnuencia).forma(forma).tipo(tipo).porcentagemAnuencia(porcentagemAnuencia)
                .aprovado(aprovado).disciplina(disciplina).build();
    }
    
    
    //Criar um controlador para aulas
    public List<Aula> getAulasFaltantes(Date dataInicio, Date dataFim, Professor professor, Disciplina disciplina) {
        List<Aula> listAulas = aulaDao.findAll().stream()
                    .filter(a -> (a.getDataHora().after(dataInicio) && 
                            a.getDataHora().before(dataFim) &&
                            a.getProfessor().getId().equals(professor.getId()) &&
                            a.getDisciplina().getId().equals(disciplina.getId())))
                    .collect(Collectors.toList());
        
        return listAulas;
    }
    
    
    public Aula geraAula(Date dataHora, 
                        List<Aluno> alunosPresente, 
                        Disciplina disciplina, 
                        Formas forma, 
                        String conteudo, 
                        Estados estado, 
                        Tipo tipo, 
                        Professor professor) {
        
        return Aula.builder().dataHora(dataHora).alunosPresente(alunosPresente).disciplina(disciplina)
                .forma(forma).conteudo(conteudo).estado(estado).tipo(tipo).professor(professor).build();
    }
    //-------------------------------------------
    
}
