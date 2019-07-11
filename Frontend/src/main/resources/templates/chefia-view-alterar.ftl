<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gerencia Chefia</title>
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
            <h1>Gerenciamento de Chefia</h1>
            <p>Essa página é responsável por fazer o geranciamento de chefias. </p>
        </div>
        <div class="row">
            <div class="col">
                <form action="/chefia/criar" method="post">
                    <div class="form-group">
                        <label for="departamento">Departamento:</label>
                        <input value="${(chefiaAtual.departamento)!}" name="departamento" type="text" class="form-control" id="departamento">
                    </div>

                    <input type="hidden" name="id" value="${(chefiaAtual.id)!}">

                    <button type="submit" class="btn btn-primary">Alterar</button>
                </form>

            </div>
        </div>
        <div class="row">
            <div class="col">
                <table class="table table-striped table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th>Departamento</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list chefias as chefia>
                            <tr>
                                <td>${chefia.nome}</td>
                                <td>
                                    <a href="/chefia/prepara-alterar?id=${chefia.id}">Alterar</a>
                                    <a href="/chefia/excluir?id=${chefia.id}">Excluir</a>
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