## Consulta Remedios Mobile Challenge

O teste consiste em construir uma aplicação nativa Android de um pseudo ecommerce de games.

### Recursos
- Siga o layout: [Layout Android](https://github.com/ConsultaRemedios/mobile-android-challenge/raw/master/Layout.sketch)
- Documentação da api no arquivo [api.md](api.md)

### Escopo
- Faça um fork deste repositório e faça as alterações no projeto já iniciado
- Os banners devem ser carregados automaticamente ao entrar no aplicativo, os banners devem vir da API
- A lista de jogos deve ser carregada automaticamente ao entrar no aplicativo, jogos devem vir da API
- Ao clicar na pesquisa, qualquer termo deve ser aceito para iniciar a busca e os resultados devem vir da API
- Ao clicar em algum banner, a url associada ao mesmo deve ser exibida internamente no app
- Ao clicar em algum game, o usuário deve ser levado a tela de detalhamento do mesmo
- No detlhamento do game, deve ser possivel adicionar o mesmo ao carrinho clicando no botao do carrinho
- Caso o produto ja esteja adicionado ao carrinho, o botao de adicionar deve ganhar a ação de remover
- Na home o ícone do carrinho deve ser flutuante e deve refletir a quantidade de produtos associados ao mesmo
- O carrinho de compra deve exibir todos os itens adicionados
- Cada produto adicionado no carrinho, soma R$10,00 ao frete total
- O frete deve se tornar grátis quando a soma de todos os produtos do carrinho for acima de R$250,00
- Deve ser possível remover produtos do carrinho
- Ao clicar em finalizar compra deve haver a validação com a API

### O que iremos avaliar...
- Funcionamento do aplicativo(ele não pode quebrar)
- Como você planejou a arquitetura como implementou novas funcionalidades
- Layout(Usabilidade e se fez conforme escopo)
- O Código(Domínio da linguagem, estrutura, uso de boas práticas, Legibilidade e Lint)
- Uso do git e github(iremos olhar os commits; branches; pull request. Recomendado uso do gitflow)

### Diferencias
- Rotinas de teste

### Como aplicar
Faça um pull request para este repositório com o código a ser avaliado

##### Recomendações
* Crie um código escalável
* Versione como se estivesse trabalhando em equipe
* Descreva em um `README.md` o que você fez de interessante, a arquitetura que usou, lista de bibliotecas que usou e as dificuldades encontradas no desenvolvimento da aplicação(Se não consegui terminar em tempo hábil, aproveite este espaço para fazer um checklist do que está faltando)
* Preze pela qualidade, se faltar tempo para terminar o teste, faça uma lista de pendências

Estou aguardando o teu pull request, boa sorte!
