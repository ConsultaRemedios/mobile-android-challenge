# Teste Mobile Android

 Aplicativo Android nativo desenvolvido como parte de processo seletivo.

## Bibliotecas utilizadas

- `Retrofit` para criar clientes HTTP de chamada assíncrona utilizando interfaces.
- `Glide` para carregar imagens de URLs e aplica-las em ImageView.
- `Room` ORM mantida pela Google para acesso ao banco de dados.

## Dificuldades

- Tive um pouco de dificuldade com o Room e uso de coroutines, provavelmten não utilizei da melhor forma, como exemplo, queria uma query para verificar se o jogo estava no carrinho mas não conseguia o retorno do repositório, então li a lista de jogos carregada no ViewModel e a partir de lá fiz essa verificação.
- Fiquei um bom tempo procurando uma biblioteca para fazer os banners, até perceber que com o ViewPager nativo seria bem mais simples.

## O quê faltou no teste

- A funcionalidade de busca de jogo pelo nome está incompleta. A busca na API utilizando texto e fala estão funcionando, mas não consegui entregar com a visualização do resultado em lista.
- Os links dos banners estão sendo abertos no browser, problemas com WebView, site da Consulta Remédios abria, mas os outros dois não, acgei melhor abrir no browser mesmo.
- Testes automatizados para componentes visuais.

## Pontos de interesse

- No fechamento do carrinho, achei confuso a somatória dos valores com o frete, preço cheio + frete = preço com desconto + frete, mas mantive como no layout.
- Foi um teste divertido de fazer, achei bem mais completo 🙂
- Obrigado pela oportunidade, boa sorte pra mim 👨‍💻
