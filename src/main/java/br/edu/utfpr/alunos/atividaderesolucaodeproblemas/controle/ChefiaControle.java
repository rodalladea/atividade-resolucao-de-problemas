/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.alunos.atividaderesolucaodeproblemas.controle;

import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.ProfessorDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.RelatorioDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.RequerimentoDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.dao.SemestreAcademicoDao;
import br.edu.utfpr.alunos.atividaderesolucaodeproblemas.entidade.Professor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rodrigo
 */
public class ChefiaControle {

    //As funções não precisam ser exatamente essas, mas são essas as ações tomadas pela chefia

    private final ProfessorDao professorDao;
    private final RequerimentoDao requerimentoDao;
    private final RelatorioDao relatorioDao;
    private final SemestreAcademicoDao semestreAcademicoDao;

    @Autowired //o que será injetado (bean do projeto)
    public ChefiaControle(ProfessorDao professorDao, RequerimentoDao requerimentoDao, RelatorioDao relatorioDao,SemestreAcademicoDao semestreAcademicoDao) {
        this.professorDao = professorDao;
        this.requerimentoDao = requerimentoDao;
        this.relatorioDao = relatorioDao;
        this.semestreAcademicoDao = semestreAcademicoDao;
    }

    // Método chamado pelo professor que solicita um plano (ou não em caso de ausência imprevista)
    public void estabelecerPlano(String tipoAusencia, Professor professor, int cargaHoraria){
        if(tipoAusencia == "previsto"){
            aprovarPlano(professor);
        }
        // Se for uma ausencia do tipo imprevista, verifico se afeta carga horária (mas o que define se afetou ou não?)
        else{
//            if(cargaHoraria é afetada pela ausencia imprevista){
//                providenciar a imediata definição da forma de compensação dos dias letivos e o cumprimento do
//                plano de trabalho
//            }else{
                // ausência imprevista que nao compromete carga horária didáca assignada no semestre em andamento
//            }
//             ausência imprevista que nao compromete carga horária didáca assignada no semestre em andamento
        }

    }

    //Quando a ausência ocorrer por motivo previsto, o docente deverá comunicar a
    //intenção de ausentar‐se à sua chefia imediata e ao coordenador do curso ao qual a disciplina está
    //vinculada,

//    public void estabelecerPlano() {
//        // implementar aqui atribuições que serão passadas por professores que determinam qual o motivo da ausência
//
//        // motivodeausencia = imprevisto;
//        // diasUteis = 3;
//
//        // disciplina vinculada ao professor
//
//        Professor prof = new Professor();
//
//
//
//        try{
//            // definir de o plano é fazer uma substituição/reposição/antecipação
//        }catch (Exception e){
////            throw new PlanoInvalidoException;
//        }
////        aprovarPlano(prof);
//    }

    // Verificar se a requisição do professor tem no mínimo 3 dias úteis de antecedência
    public boolean aprovarPlano(Professor prof){ //throws planoInvalidoException{
        if(prof.getFaltas() >= 3){
            // Se der tudo certo
            return true;
        }
        else{
            //requisição abaixo dos 3 dias

            // Com esse resultado 'false', tomar as devidas providências legais, conforme previsto em lei.
            return false;
        }
    }

//    Compete à Diretoria de Graduação e Educação Profissional dar encaminhamento
//    aos registros recebidos das Chefias de Departamento e/ou Coordenações de Curso para as devidas providências legais.

//      Método a ser implementado em Dirgrad.java (pelo que entendi)
//    public void relatorioDirgrad() {
//
//    };
    
}
