/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.RelatorioDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Chefia;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Dirgrad;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Relatorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rodrigo
 */

@Service
public class RelatorioControle {
    
    @Autowired
    private RelatorioDao relatorioDao;
    
    public void salva(List<Professor> professores, Dirgrad dirgrad, Chefia chefia) {
        Relatorio relatorio = Relatorio.builder().professores(professores).dirgrad(dirgrad).chefia(chefia)
                .observacao(null).providencia(null).build();
        
        relatorioDao.save(relatorio);
    }
}
