# Mini Autorizador

- Permite a criação de um cartão com saldo inicial de R$500,00;
- Permite obtenção do saldo do cartao;
- Realiza transações debitando o saldo do cartao;
- Realiza validação de regras tais como (SALDO_INSUFICIENTE|SENHA_INVALIDA|CARTAO_INEXISTENTE|ERRO_DE_AUTENTICACAO)


## Recomendações de utilização

- o arquivo de configuração yml do docker se encontra em: /mini-autorizador/src/main/resources/docker-compose.yml
- os testes devem ser realizados na versão JUnit 4 e se encontram em: /mini-autorizador/src/test/java/br/com/vr/autorizador/
- há também collections que podem ser utilizadas via Postman que se encontram em: /mini-autorizador/src/main/resources/Autorizador.postman_collection.json
- existe um arquivo dump.sql utilizado para criar a tabela cartao ao executar o docker file e se encontra em: /mini-autorizador/src/main/resources/dump/dump.sql </br>
	*importante que esta permaneça nesta pasta "dump" e esta pasta na raiz de onde se executará o docker file;