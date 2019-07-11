<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gerencia Dirgrad</title>
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
            <h1>Gerenciamento de Dirgrad</h1>
            <p>Essa página é responsável por fazer o geranciamento de dirgrads. </p>
        </div>
        <div class="row">
            <div class="col">
                <form action="/dirgrad/criar" method="post">
                    <div class="form-group">
                        <label for="id">Identificador:</label>
                        <input value="${(dirgradAtual.id)!}" name="id" type="number" class="form-control" id="id">
                    </div>

                    <input type="hidden" name="id" value="${(dirgradAtual.id)!}">

                    <button type="submit" class="btn btn-primary">Alterar</button>
                </form>

            </div>
        </div>
        <div class="row">
            <div class="col">
                <table class="table table-striped table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th>Id do Dirgrad</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list dirgrads as dirgrad>
                            <tr>
                                <td>${dirgrad.id}</td>
                                <td>
                                    <a href="/dirgrad/prepara-alterar?id=${dirgrad.id}">Alterar</a>
                                    <a href="/dirgrad/excluir?id=${dirgrad.id}">Excluir</a>
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