<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gerencia Aula</title>
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
            <h1>Gerenciamento de Aula</h1>
            <p>Essa página é responsável por fazer o geranciamento de aulas. </p>
        </div>
        <div class="row">
            <div class="col">
                <form action="/aula/criar" method="post">
                    <div class="form-group">
                        <label for="dataHora">Data e Hora:</label>
                        <input value="${(aulaAtual.dataHora)!}" name="dataHora" type="text" class="form-control" id="dataHora">
                    </div>
                    <div class="form-group">
                        <label for="alunosPresentes">Alunos presentes:</label>
                        <input value="${(aulaAtual.alunosPresentes)!}"  name="alunosPresentes" type="text" class="form-control" id="alunosPresentes">
                    </div>
                    <div class="form-group">
                        <label for="disciplina">Disciplina:</label>
                        <input value="${(aulaAtual.disciplina)!}"  name="disciplina" type="number" class="form-control" id="disciplina">
                    </div>

                     <div class="form-group">
                        <label for="forma">Forma:</label>
                        <input value="${(aulaAtual.forma)!}"  name="forma" type="text" class="form-control" id="forma">
                    </div>

                     <div class="form-group">
                        <label for="conteudo">Conteudo:</label>
                        <input value="${(aulaAtual.conteudo)!}"  name="conteudo" type="text" class="form-control" id="conteudo">
                    </div>

                     <div class="form-group">
                        <label for="estado">Estado:</label>
                        <input value="${(aulaAtual.estado)!}"  name="estado" type="text" class="form-control" id="estado">
                    </div>

                     <div class="form-group">
                        <label for="tipo">Tipo:</label>
                        <input value="${(aulaAtual.tipo)!}"  name="tipo" type="text" class="form-control" id="tipo">
                    </div>

                    <#--  Os atributos com ManyToOne: não sei se precisa  -->
                     <#--  <div class="form-group">
                        <label for="sigla">Sigla:</label>
                        <input value="${(aulaAtual.sigla)!}"  name="sigla" type="text" class="form-control" id="sigla">
                    </div>

                     <div class="form-group">
                        <label for="sigla">Sigla:</label>
                        <input value="${(aulaAtual.sigla)!}"  name="sigla" type="text" class="form-control" id="sigla">
                    </div>  -->
                    
                    <input type="hidden" name="id" value="${(aulaAtual.id)!}">

                    <button type="submit" class="btn btn-primary">Alterar aula</button>
                </form>

            </div>
        </div>
        <div class="row">
            <div class="col">
                <table class="table table-striped table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th>Data e Hora</th>
                            <th>Alunos presentes</th>
                            <th>Disciplina</th>
                            <th>Forma</th>
                            <th>Conteudo</th>
                            <th>Estado</th>
                            <th>Tipo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list aulas as aula>
                            <tr>
                                <td>${aula.dataHora}</td>
                                <td>${aula.alunosPresentes}</td>
                                <td>${aula.disciplina}</td>
                                <td>${aula.forma}</td>
                                <td>${aula.conteudo}</td>
                                <td>${aula.estado}</td>
                                <td>${aula.tipo}</td>
                                <td>
                                    <a href="/aula/prepara-alterar?id=${aula.id}">Alterar</a>
                                    <a href="/aula/excluir?id=${aula.id}">Excluir</a>
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