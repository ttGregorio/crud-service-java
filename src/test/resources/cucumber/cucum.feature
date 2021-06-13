Feature: Este arquivo tem por objetivo exemplificar a utilizacao do cucumber para a execucao de testes integrados

  Scenario Outline: Este cenario tem por objetivo o post do servico
    Given De nome <name>
    When executar o post para o servico
    Then Deve retornar o status <status> na requisicao

    Examples:
      |	name	|	status		  |
      |	User	|	200					|
      |	 			|	400					|
      
  Scenario Outline: Este cenario tem por objetivo testar o put do servico
    Given Nome <name>, id do objeto <id> e id do header <id_header>
    When executar o put para o servico
    Then Deve retornar o status <status> na requisicao

    Examples:
      |	id_header		|	id		|	name	|	status		  |
      |	1						|	1			|	User	|	200					|
      |	1 					|	1 		|	 			|	400					|
      |	1 					|	  		|	User	|	400					|
      |							|	1			|	User	|	400					|
      |	2						|	1			|	User	|	400					|
      
  Scenario Outline: Este cenario tem por objetivo testar a busca por id
    Given De id <id>
    When executar o get por id para o servico
    Then Deve retornar o status <status> na requisicao

    Examples:
      |	id		|	status		  |
      |	1			|	200					|
      |	2			|	400					|
      
  Scenario Outline: Este cenario tem por objetivo testar o delete por id
    Given De id <id>
    When executar o delete para o servico
    Then Deve retornar o status <status> na requisicao

    Examples:
      |	id		|	status		  |
      |	1			|	200					|
      |	2			|	400					|
      
Scenario: Este cenario tem por objetivo testar a listagem de itens
    When executar o get para o servico
    Then Deve retornar o status 200 na requisicao
      