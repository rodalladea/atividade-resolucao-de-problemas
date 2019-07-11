<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gerencia Requerimento</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>

<body>
    <div class="container">
        <div class="jumbotron">
            <h1>Gerenciamento de Requerimento</h1>
            <p>Essa página é responsável por fazer o geranciamento de requerimentos. </p>
        </div>
        <div class="row">
            <div class="col">
                <form action="/requerimento/criar" method="post">
                    <div class="form-group">
                        <label for="dataInicio">Data de Início:</label>
                        <input value="${(requerimentoAtual.dataInicio)!}" name="dataInicio" type="text" class="form-control" id="dataInicio">
                    </div>
                    <div class="form-group">
                        <label for="dataTermino">Data de Término:</label>
                        <input value="${(requerimentoAtual.dataTermino)!}"  name="dataTermino" type="text" class="form-control" id="dataTermino">
                    </div>

                    <div class="form-group">
                        <label for="aulasFaltantes">Aulas faltantes:</label>
                        <input value="${(requerimentoAtual.aulasFaltantes)!}"  name="aulasFaltantes" type="text" class="form-control" id="aulasFaltantes">
                    </div>

                    <div class="form-group">
                        <label for="aulasReposicao">Aulas reposição:</label>
                        <input value="${(requerimentoAtual.aulasReposicao)!}" name="aulasReposicao" type="text" class="form-control" id="aulasReposicao">
                    </div>
                    <div class="form-group">
                        <label for="listaAnuencia">Lista de Anuência:</label>
                        <input value="${(requerimentoAtual.listaAnuencia)!}"  name="listaAnuencia" type="text" class="form-control" id="listaAnuencia">
                    </div>
                    <div class="form-group">
                        <label for="tipo">Tipo:</label>
                        <input value="${(requerimentoAtual.tipo)!}"  name="tipo" type="text" class="form-control" id="tipo">
                    </div>

                    <div class="form-group">
                        <label for="status">Status:</label>
                        <input value="${(requerimentoAtual.status)!}" name="status" type="text" class="form-control" id="status">
                    </div>
                    <div class="form-group">
                        <label for="falta">Falta:</label>
                        <input value="${(requerimentoAtual.falta)!}"  name="falta" type="text" class="form-control" id="falta">
                    </div>
                    <div class="form-group">
                        <label for="porcentagemAnuencia">Porcentagem de Anuência:</label>
                        <input value="${(requerimentoAtual.porcentagemAnuencia)!}"  name="porcentagemAnuencia" type="number" class="form-control" id="porcentagemAnuencia">
                    </div>

                    <div class="form-group">
                        <label for="aprovado">Aprovado:</label>
                        <input value="${(requerimentoAtual.aprovado)!}"  name="aprovado" type="boolean" class="form-control" id="aprovado">
                    </div>

                    <#--  ManyToOne não sei se precisa, mas deixei pronto caso precise  -->
                    <#--  <div class="form-group">
                        <label for="professor">Professor:</label>
                        <input value="${(requerimentoAtual.professor)!}"  name="professor" type="text" class="form-control" id="professor">
                    </div>
                    <div class="form-group">
                        <label for="chefia">Chefia:</label>
                        <input value="${(requerimentoAtual.chefia)!}" name="chefia" type="text" class="form-control" id="chefia">
                    </div>
                    <div class="form-group">
                        <label for="disciplina">Disciplina:</label>
                        <input value="${(requerimentoAtual.disciplina)!}"  name="disciplina" type="text" class="form-control" id="disciplina">
                    </div>  -->

                    <input type="hidden" name="id" value="${(requerimentoAtual.id)!}">

                    <button type="submit" class="btn btn-primary">Alterar</button>
                </form>

            </div>
        </div>
        <div class="row">
            <div class="col">
                <table class="table table-striped table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th>Data de Início</th>
                            <th>Data de Término</th>
                            <th>Aulas faltantes</th>
                            <th>Aulas reposição</th>
                            <th>Lista de Anuência</th>
                            <th>Tipo</th>
                            <th>Status</th>
                            <th>Falta</th>
                            <th>Porcentagem de Anuência</th>
                            <th>Aprovado</th>
                            <#--  <th>Professor</th>  -->
                            <#--  <th>Chefia</th>  -->
                            <#--  <th>Disciplina</th>  -->
                        </tr>
                    </thead>
                    <tbody>
                        <#list requerimentos as requerimento>
                            <tr>
                                <td>${requerimento.dataInicio}</td>
                                <td>${requerimento.dataTermino}</td>
                                <td>${requerimento.aulasFaltantes}</td>
                                <td>${requerimento.aulasReposicao}</td>
                                <td>${requerimento.listaAnuencia}</td>
                                <td>${requerimento.tipo}</td>
                                <td>${requerimento.status}</td>
                                <td>${requerimento.falta}</td>
                                <td>${requerimento.porcentagemAnuencia}</td>
                                <td>${requerimento.aprovado}</td>
                                <#--  <td>${requerimento.professor}</td>  -->
                                <#--  <td>${requerimento.chefia}</td>  -->
                                <#--  <td>${requerimento.disciplina}</td>  -->
                                <td>
                                    <a href="/requerimento/prepara-alterar?id=${requerimento.id}">Alterar</a>
                                    <a href="/requerimento/excluir?id=${requerimento.id}">Excluir</a>
                                </td>
                            </tr>        
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>

</html>