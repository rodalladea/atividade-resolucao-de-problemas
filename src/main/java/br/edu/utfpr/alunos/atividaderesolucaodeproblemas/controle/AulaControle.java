/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.AulaDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aluno;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Disciplina;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Estados;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Formas;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Tipo;
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
public class AulaControle {
    
    @Autowired
    private AulaDao aulaDao;
    
    public void salvaTodas(List<Aula> aulas) {
        aulaDao.saveAll(aulas);
    }
        
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
}
