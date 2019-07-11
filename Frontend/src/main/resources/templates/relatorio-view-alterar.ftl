<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gerencia Relatório</title>
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
            <h1>Gerenciamento de Relatório</h1>
            <p>Essa página é responsável por fazer o geranciamento de relatórios. </p>
        </div>
        <div class="row">
            <div class="col">
                <form action="/relatorio/criar" method="post">
                    <div class="form-group">
                        <label for="professores">Professores:</label>
                        <input value="${(relatorioAtual.professores)!}" name="professores" type="text" class="form-control" id="professores">
                    </div>

                    <div class="form-group">
                        <label for="observacao">Observação:</label>
                        <input value="${(relatorioAtual.observacao)!}" name="observacao" type="text" class="form-control" id="observacao">
                    </div>

                    <div class="form-group">
                        <label for="providencia">Providência:</label>
                        <input value="${(relatorioAtual.providencia)!}" name="providencia" type="text" class="form-control" id="providencia">
                    </div>

                    <#--  ManyToOne: não sei se precisa, mas deixei pronto caso precise  -->
                    <#--  <div class="form-group">
                        <label for="chefia">Chefia:</label>
                        <input value="${(relatorioAtual.chefia)!}"  name="chefia" type="text" class="form-control" id="chefia">
                    </div>
                    <div class="form-group">
                        <label for="dirgrad">Dirgrad:</label>
                        <input value="${(relatorioAtual.dirgrad)!}"  name="dirgrad" type="number" class="form-control" id="dirgrad">
                    </div>  -->

                    <input type="hidden" name="id" value="${(relatorioAtual.id)!}">

                    <button type="submit" class="btn btn-primary">Alterar</button>
                </form>

            </div>
        </div>
        <div class="row">
            <div class="col">
                <table class="table table-striped table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th>Professores</th>
                            <th>Observação</th>
                            <th>Providência</th>
                            <#--  <th>Chefia</th>  -->
                            <#--  <th>Dirgrad</th>  -->
                        </tr>
                    </thead>
                    <tbody>
                        <#list relatorios as relatorio>
                            <tr>
                                <td>${relatorio.professores}</td>
                                <td>${relatorio.observacao}</td>
                                <td>${relatorio.providencia}</td>
                                <#--  <td>${relatorio.chefia}</td>  -->
                                <#--  <td>${relatorio.dirgrad}</td>  -->
                                <td>
                                    <a href="/relatorio/prepara-alterar?id=${relatorio.id}">Alterar</a>
                                    <a href="/relatorio/excluir?id=${relatorio.id}">Excluir</a>
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