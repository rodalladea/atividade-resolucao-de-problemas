/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.ProfessorDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aluno;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Estados;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Falta;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Formas;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Status;
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
    
    @Autowired
    private ProfessorDao professorDao;
    
    private final AulaControle aulaControle = new AulaControle();
    private final RequerimentoControle requerimentoControle = new RequerimentoControle();
    private final ChefiaControle chefiaControle = new ChefiaControle();
    
    public List<Professor> listaProfessoresFaltantes() {
        return professorDao.findAll().stream()
                .filter(p -> p.getFaltas() > 0)
                .collect(Collectors.toList());
    }
    
    //Paramentros que serão inseridos pelo usuário professor
    public void requisitarReposicao(Date dataInicio,
                                    Date dataFim,
                                    Professor professor,
                                    Disciplina disciplina,
                                    Falta falta) throws ParseException {
        
        //Descobre aulas que ira faltar em uma determinado disciplina
        List<Aula> aulasFaltantes = aulaControle.getAulasFaltantes(dataInicio, dataFim, professor, disciplina);
        //Quantidade de dias que ira faltar
        int quantDias = (int) (((((dataFim.getTime() - dataInicio.getTime())/1000)/60)/60)/24);
        //Qual é a chefia do professor
        Chefia chefia = chefiaControle.getChefiaByProfessor(professor);
        
        //Se quantidade manor que 15 dias o professor define o plano de aula para reposição
        if (quantDias <= 15 && falta.equals(Falta.PREVISTO)) {
            List<Aula> aulasReposicao = new ArrayList<>();
            List<Aluno> listaAnuencia = new ArrayList<>();
            
            //Define o plano de aulas
            geraPlanoAula(aulasFaltantes, aulasReposicao, professor);
            
            //Verifica anuencia
            for (int i = 0; i < disciplina.getAlunosMatriculados().size(); i ++) {
                if (true && disciplina.getAlunosMatriculados().get(i).getPorcentagemPresenca() > 75) { //Pode ser tanto true quanto false, o professor vai selecionando
                    listaAnuencia.add(disciplina.getAlunosMatriculados().get(i));
                }
            }
            
            //Calcula porcentagem de anuencia
            double porcentagemAnuencia = (listaAnuencia.size()/disciplina.getAlunosMatriculados().size()) * 100;
            
            //Gera requerimento com o plano feito
            requerimentoControle.salva(dataInicio, dataFim, professor, chefia, disciplina,
                    aulasFaltantes, aulasReposicao, listaAnuencia, Formas.PRESENCIAL, 
                    Tipo.MENOR_15, Status.COMPLETO, falta, porcentagemAnuencia, false);
            
        } else {
                //Gera requerimento com plano para o dirgrad fazer, pois faltara mais de 15 dias
                requerimentoControle.salva(dataInicio, dataFim, professor, chefia, disciplina,
                        aulasFaltantes, null, null, null, 
                        Tipo.MAIOR_15, Status.COMPLETO, falta, 0, false);
            
        }
        
    }
    
    public void geraPlanoAula(List<Aula> aulasFaltantes, 
                                    List<Aula> aulasReposicao, Professor professor) throws ParseException {
        //variaveis aleatorias criadas para inserção, que no caso o usuario iria inserindo
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dataAula = "25/05/2019 15:30:00";
        String conteudo = "conteudo";

        //Define o plano de aulas
        for (int i = 0; i < aulasFaltantes.size(); i++) {
            Aula aula = aulaControle.geraAula(dateFormat.parse(dataAula),
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
    
}
