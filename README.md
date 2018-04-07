# jboss-arquillian
exemplos utilizando jboss + testes de integração com arquillian

# referência
https://www.devmedia.com.br/testes-de-integracao-utilizando-cdi-jpa-e-ejb-e-arquillian/33340

# procedimento
 1. Editar o arquivo **arquillian.xml** e adicionar o diretório local do wildfly entre as tags **property**
 2. Executar a classe **PessoaDaoTeste.java** com o JUnit
 
OPERAÇÕES
===========================================

**GET**

http://localhost:8080/arquillian-poc/rest/pessoa

http://localhost:8080/arquillian-poc/rest/pessoa/1

**POST**

http://localhost:8080/arquillian-poc/rest/pessoa
{
 "nome": "Nome Sobrenome",
 "idade": 20
}

**PUT**

http://localhost:8080/arquillian-poc/rest/pessoa/1
{
 "nome": "Nome Meio Sobrenome",
 "idade": 20
}

**DELETE**

http://localhost:8080/arquillian-poc/rest/pessoa/1

EXEMPLO PUSH NOTIFICATION
===========================================
Referência: 
https://yakovfain.com/2014/12/29/pushing-data-to-multiple-websocket-clients-from-a-java-server/

URL para teste:
http://localhost:8080/arquillian-poc/clock.html