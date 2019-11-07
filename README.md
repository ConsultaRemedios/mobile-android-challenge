# Teste Mobile Android

 Aplicativo Android nativo desenvolvido como parte de processo seletivo.

## Bibliotecas utilizadas

- `Retrofit` para criar clientes HTTP de chamada assíncrona utilizando interfaces.
- `Glide` para carregar imagens de URLs e aplica-las em ImageView.
- `Pluto` um slider para imagem/gif baseado em `RecyclerView`.

## Pontos de interesse

- Para a listagem inicial de jogos o `RecyclerView` e adaptado com o `StaggeredGridLayoutManager` para dar um visual com alturas diferentes dos itens na listagem de duas colunas.
- O token de acesso a API está armazenado 'fisicamente' fora do projeto, utilizando `gradle.properties`. Se fosse uma situação de informação sensível que não devesse ser compartilhada no GitHub esta seria uma opção para não expor a mesma.

## Dificuldades

- Inicialmente a maior dificuldade prevista seria com a linguagem Kotlin, pela falta de contato, porém a linguagem se mostrou bastante simplificada e produtiva, não sendo um problema.
- Minha maior dificuldade foi em encontrar uma biblioteca para textos expansíveis, para uso na descrição do jogo, sendo que nenhum deu mesmo certo. No final, desenvolvi uma solução simples para esse comportamento.

## O quê faltou no teste

- Refatoração do código. Apesar de ser uma mudança simples, as classes Activity não possuem um pacote próprio e mais importante o código apresenta algumas inconsistências de nomenclatura que não foram possíveis dar maior atenção por falta de tempo hábil.
- Na ActionBar eu pretendia implementar o funcionamento de busca, pelo nome do jogo, na activity inicial, e o icone de carrinho de compras ficou sem o badge de auntidade de itens, fiz um teste pra incluir esse feature, mas sem sucesso, e tambem por falta de tempo ambas não foram implementadas.
- Quanto a testes, fiz somente as chamadas da API.
