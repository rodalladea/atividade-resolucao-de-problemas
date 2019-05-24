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
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.excecoes.ExceptionMaxDia;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level; //importação não utilizada
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rodrigo
 */

public class AulaControle extends CrudTemplate<Aula>{
    
    @Autowired
    private AulaDao aulaDao;
    
    @Override
    protected void salva(Aula entidade) throws ExceptionMaxDia {
        
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
        Date dia = entidade.getDataHora();

        if (this.getAulasDoDia(dia, entidade.getDisciplina()).size() <= 6)
            aulaDao.save(entidade);
        else
            throw new ExceptionMaxDia(df.format(dia));
        
    }

    @Override
    protected void exclui(Aula entidade) {
        aulaDao.delete(entidade);
    }

    @Override
    protected void atualiza(Aula entidade) {
        this.exclui(entidade);
        try {
            this.salva(entidade);
        } catch (ExceptionMaxDia ex) {
            Logger.getLogger(ex.getMessage());
        }
    }

    @Override
    protected List<Aula> listaTodos() {
        return aulaDao.findAll();
    }
    
    public void salvaTodas(List<Aula> aulas) {
        aulas.stream().forEach(a -> { //poderia usar somente o forEach sem o stream()
            try {
                this.salva(a);
            } catch (ExceptionMaxDia ex) {
                Logger.getLogger(ex.getMessage());
            }
        });
    }
        
    public List<Aula> getAulasFaltantes(Date dataInicio, Date dataFim, Professor professor, Disciplina disciplina) {
        return this.listaTodos().stream()
                    .filter(a -> (a.getDataHora().after(dataInicio) && 
                            a.getDataHora().before(dataFim) &&
                            a.getProfessor().getId().equals(professor.getId()) &&
                            a.getDisciplina().getId().equals(disciplina.getId())))
                    .collect(Collectors.toList());
        
    }
    
    public List<Aula> getAulasEsquecidas() {
        return this.listaTodos().stream()
                    .filter(a -> a.getEstado().equals(Estados.NAO_FEITA))
                    .collect(Collectors.toList());
    }
    
    public List<Aula> getAulasDisciplina(Disciplina disciplina) {
        return this.listaTodos().stream()
                    .filter(a -> a.getDisciplina().equals(disciplina))
                    .collect(Collectors.toList());
    }
    
    public List<Aula> getAulasComAluno(Aluno aluno) {
        return this.listaTodos().stream()
                .filter(a -> a.getAlunosPresente().stream().anyMatch(al -> al.equals(aluno)))
                .collect(Collectors.toList());
    }
    
    public List<Aula> getAulasDoDia(Date data, Disciplina disciplina) { //poderia ser um método privado já que só é utilizado dentro dessa classe
        return this.listaTodos().stream()
                .filter(a -> a.getDataHora().equals(data) &&
                        a.getDisciplina().equals(disciplina))
                .collect(Collectors.toList());
    }
    
    public Aula geraAula(Date dataHora, 
                        List<Aluno> alunosPresente, 
                        Disciplina disciplina, 
                        Formas forma, 
                        String conteudo, 
                        Estados estado, 
                        Tipo tipo, 
                        Professor professor) { //método tem muitos parâmetros (8 no total)
        
        return Aula.builder().dataHora(dataHora).alunosPresente(alunosPresente).disciplina(disciplina)
                .forma(forma).conteudo(conteudo).estado(estado).tipo(tipo).professor(professor).build();
    }
}
