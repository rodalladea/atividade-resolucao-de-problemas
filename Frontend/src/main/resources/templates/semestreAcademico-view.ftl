<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gerencia Semestre Acadêmico</title>
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
            <h1>Gerenciamento de semestre acadêmico</h1>
            <p>Essa página é responsável por fazer o geranciamento de semestre acadêmico. </p>
        </div>
        <div class="row">
            <div class="col">
                <form action="/semestreAcademico/criar" method="post">
                    
                    <div class="form-group">
                        <label for="id">Id:</label>
                        <input value="${(semestreAcademicoAtual.id)!}" name="id" type="number" class="form-control" id="id">
                    </div>

                    <div class="form-group">
                        <label for="dataInicio">Data de Início:</label>
                        <input value="${(semestreAcademicoAtual.dataInicio)!}" name="dataInicio" type="text" class="form-control" id="dataInicio">
                    </div>
                    <div class="form-group">
                        <label for="dataTermino">Data de Término:</label>
                        <input value="${(semestreAcademicoAtual.dataTermino)!}"  name="dataTermino" type="text" class="form-control" id="dataTermino">
                    </div>

                    <button type="submit" class="btn btn-primary">Criar</button>
                </form>

            </div>
        </div>
        <div class="row">
            <div class="col">
                <table class="table table-striped table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th>Id</th>
                            <th>Data de Início</th>
                            <th>Data de Término</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list semestresAcademicos as semestreacademico>
                            <tr>
                                <td>${semestreacademico.id}</td>
                                <td>${semestreacademico.dataInicio}</td>
                                <td>${semestreacademico.dataTermino}</td>
                                <td>
                                    <a href="/semestreacademico/prepara-alterar?id=${semestreacademico.id}">Alterar</a>
                                    <a href="/semestreacademico/excluir?id=${semestreacademico.id}">Excluir</a>
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