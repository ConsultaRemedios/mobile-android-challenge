## Teste para vaga desenvolvedor Mobile em ConsultaRemedios

O teste consiste em construir uma aplicação nativa Android de um pseudo ecommerce de games.

### Informações sobre o Projeto
- O projeto foi elaborado utilizando Clean Architecture com padrão MVP
- Linguagem utilizada:
   - Kotlin
- Bibliotecas utilizadas na implementação: 
   - Retrofit2: moshi-converter, adapter-rxjava2; 
   - RxJava2;
   - Glide;
   - AndroidX;
- Bibliotecas utilizadas para testes:
   - Espresso;
   - Mockito;
   - JUnit4;
   
### Requisitos pedidos
- A lista de jogos deve ser carregada automaticamente ao entrar no aplicativo, jogos devem vir da API
- Ao clicar em algum item da lista, ir para tela de detalhes
- O carrinho de compra deve exibir todos os itens adicionados.
- Cada produto adicionado no carrinho, soma R$:10,00 ao frete total.
- O frete deve se tornar grátis quando a soma de todos os produtos do carrinho for acima de R$:250,00.

##### Informações Adicionais do Desenvolvedor
O projeto veio com um layout pré-estabelecido para todas as telas. No entanto, optei por alterar pequenas partes a fim de melhorar a usabilidade, são elas:
   - Como a API responde com apenas uma imagem por jogo, não é necessário adicionar um SlideShow. De qualquer forma, importei um de uma biblioteca externa. Mas optei por remover devido a erros que surgiram na parte de testes (Algo de conflito entre Kotlin, Mockito e Maven)
   - Há um campo para alterar a quantidade na tela do carrinho, mas pelo layout fornecido não ficou claro se era um dropdown com números ou EditText. Por isso, optei por alterar o layout, adicionando um botão para adicionar e outro para remover a quantidade do item do carrinho.
   - Ainda na Tela do Carrinho, o layout fornecido não mostra multiplos itens inclusos. Mas como foi pedido para exibir todos os itens adicionados, criei uma estrutura com Adapter e RecyclerView
   - Há imagens com fundo branco em vez de transparente vindo do backend. Para evitar problemas de design, acredito que há duas soluções possíveis:
      1 - Permitir apenas imagens com fundo branco no backend;
      2 - Remover o background do Card de Imagem no Layout, assim não fica estranho quando imagens diferentes aparecem;
   - O Backend não retorna um campo para descrição dos jogos. Logo, esta parte não apareceria no layout e o botão Ler Mais não faz sentido. Além disso, esse botão Ler Mais não é uma boa prática em aplicativos Android, pois não utiliza todo o potencial que temos facilmente em mãos. Adicionar um ScrollView para a descrição faz mais sentido do que ter um botão Ler Mais. 
   - Me parece que este layout foi baseado em Apps Híbridos, já trabalhei em um e-commerce em ionic com layout semelhante.
   - Optei por utilizar o padrão MVP, pois ele facilita na criação de testes e deixa o código mais limpo e legível.
   
## Dificuldades
   Este projeto foi um bom desafio. Seguir um layout pré-definido ajudou bastante, mas também trouxe algumas dúvidas durante o desenvolvimento, principalmente devido à inconsistência entre o que foi fornecido pelo backend e o que foi pedido no layout.
   
## Sugestões/Pendências
   - Por falta de tempo, faltou adicionar testes unitários para métodos que chamam um serviço.
   - Criar alguma forma de geração automática de Token no backend para evitar expor o token de acesso no código do app.
   - Criar estruturas para salvar o carrinho
   - Senti a falta de jogos em promoção no layout. O cadastro de promoções é uma funcionalidade que todo MarketPlace tem, pois é uma boa estratégia de marketing de vendas.
