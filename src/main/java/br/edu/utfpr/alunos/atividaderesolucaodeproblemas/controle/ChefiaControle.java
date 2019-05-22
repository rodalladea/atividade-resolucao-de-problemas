/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;


import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.ChefiaDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Aula;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Dirgrad;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Falta;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Formas;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Requerimento;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Status;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Tipo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author rodrigo
 */

@Service
public class ChefiaControle {

    
    @Autowired
    private ChefiaDao chefiaDao;
    
    
    private final AulaControle aulaControle = new AulaControle();
    private final ProfessorControle professorControle = new ProfessorControle();
    private final RequerimentoControle requerimentoControle = new RequerimentoControle();
    private final RelatorioControle relatorioControle = new RelatorioControle();
    
    public Chefia getChefiaByProfessor(Professor professor) {
        return chefiaDao.findAll().stream()
                .filter(c -> c.getDepartamento().equalsIgnoreCase(professor.getDepartamento()))
                .findAny()
                .get();
    }

    // Método chamado pelo professor que solicita um plano (ou não em caso de ausência imprevista)
    public void estabelecerPlano(Professor professor, Requerimento requerimento) throws ParseException{
        
        if(requerimento.getTipo().equals(Tipo.MENOR_15) && requerimento.getFalta().equals(Falta.PREVISTO)) {
            avaliar(requerimento);
            
        } else {
            requerimentoControle.exclui(requerimento);
            
            List<Aula> aulasReposicao = new ArrayList<Aula>();
                    
            professorControle.geraPlanoAula(requerimento.getAulasFaltantes(), aulasReposicao, professor); //fazer esse metodo no sistemaControle
            
            requerimento.setAulasReposicao(aulasReposicao);
            defineForma(requerimento);
            requerimento.setStatus(Status.ESPERANDO_ANUENCIA);
            
            requerimentoControle.salva(requerimento);
        }

    }

    public void avaliar(Requerimento requerimento){
        boolean aprovacao = true; //dado recebido do usuario
        
        if (aprovacao) {
            requerimento.setAprovado(true);
            aulaControle.salvaTodas(requerimento.getAulasReposicao()); //adicionar no aula controle
        } else {
            requerimento.setAprovado(false);
        }
    }
    
    public void defineForma(Requerimento requerimento) {
        String forma = "presencial"; //pego do usuario
        
        if (forma.equalsIgnoreCase("presencial")) {
            requerimento.setForma(Formas.PRESENCIAL);
        } else {
            requerimento.setForma(Formas.NAO_PRESENCIAL);
        }
    }
    
    public void relatorioDirgrad(Dirgrad dirgrad, Chefia chefia) {
        List<Professor> professoresFaltanates = professorControle.listaProfessoresFaltantes();
        
        relatorioControle.salva(professoresFaltanates, dirgrad, chefia);
    };
    
    
    
}
