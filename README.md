# Stoom
 Brainweb & Stoom
 
 # O que você vai achar neste projeto ?
 É uma aplicação Spring Boot, com JPA, onde foi realizado um serviço de cadastro de endereços.
 Dentro do serviço de endereços existe as operações de Criação, Leitura, Atualização de Cadastro e Remoção.
 
 Você pode observar um pouco dos detalhes no endereço http://localhost:8080/swagger-ui.html#/address45resource após iniciar a aplicação.
 A página será posicionado da seguinte forma:
 
  ![alt text](https://i.imgur.com/aSIN1sV.png)
 
# Informações interessantes
- No projeto foi utilizado o Geocoding API do Google (A chave esta funcional por tempo indeterminado)
Esta ferramenta esta sendo utilizado para auxiliar com as informações de Latitude e Longitude do cadastro de Endereço.
Veja um exemplo de uma requisição sem as informações:

![alt text](https://i.imgur.com/nCzhUfr.png)

- Também foi utilizado o JUnit para realização dos testes unitarios.
Os testes podem ser executados dentro do projeto.

![alt text](https://i.imgur.com/iMDCyk6.png)

![alt text](https://i.imgur.com/y7M1lmS.png)

- Foi implementado o Log4j2 para adicionar informações/problemas na aplicação

Veja um exemplo

![alt text](https://i.imgur.com/CDUw8vv.png)

# Dockerfile

Também existe o dockerfile para subir sua aplicação utilizando Docker.
1. É necessário realizar um Maven install utilizando a seguinte opção:

![alt text](https://i.imgur.com/amk7WqG.png)

2. Dentro do docker rodar o comando 
> "docker build -t stoom.jar"

3. Executar o comando abaixo para listar as imagens
> "docker image ls"

4. Por fim, executar o comando para iniciar o docker
> "docker run -p 9090:8080 stoom.jar"

