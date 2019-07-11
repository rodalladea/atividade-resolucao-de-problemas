<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gerencia Aluno</title>
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
            <h1>Gerenciamento de Aluno</h1>
            <p>Essa página é responsável por fazer o geranciamento de alunos. </p>
        </div>
        <div class="row">
            <div class="col">
                <form action="/aluno/criar" method="post">
                    <div class="form-group">
                        <label for="porcentagemPresenca">Porcentagem de presença:</label>
                        <input value="${(alunoAtual.porcentagemPresenca)!}" name="porcentagemPresenca" type="text" class="form-control" id="porcentagemPresenca">
                    </div>

                    <#--  Deixei comentado por não sei se precisa  -->
                    <#--  <div class="form-group">
                        <label for="disciplinas">Disciplina</label>
                        <input value="${(alunoAtual.disciplinas)!}"  name="disciplinas" type="text" class="form-control" id="disciplinas">
                    </div>  -->

                    <button type="submit" class="btn btn-primary">Criar aluno</button>
                </form>

            </div>
        </div>
        <div class="row">
            <div class="col">
                <table class="table table-striped table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th>Porcentagem de de presença</th>
                            <#--  <th>Disciplina</th>  -->
                        </tr>
                    </thead>
                    <tbody>
                        <#list alunos as aluno>
                            <tr>
                                <td>${aluno.nome}</td>
                                <td>${aluno.disciplinas}</td>
                                <td>
                                    <a href="/aluno/prepara-alterar?id=${aluno.id}">Alterar</a>
                                    <a href="/aluno/excluir?id=${aluno.id}">Excluir</a>
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