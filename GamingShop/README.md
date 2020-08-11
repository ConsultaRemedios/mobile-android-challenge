# Projeto Gaming Shop

O projeto tem como objetivo  desenvolver um aplicativo e-commerce de jogos que simule uma conexão real com servidor 
e seja projetado com uma arquitetura que faça a separação das camadas do aplicativo.

## O projeto está incompleto, as pendências estão listadas abaixo:
 - Search view na Main screen
 - Número de itens no carrinho não é mostrado no Floating Action Button na Main Screen
 - UI não está formatando corretamente os valores em dinheiro (falta formatar com duas casas decimais e com vírgula)
 - Falta testes unitários (a cadamada de regra de negócio foi praticamente toda isolada, mas não foi feito testes ainda)
 - Falta testes de UI com Espresso
 - Carrinho não remove itens
 - Carrinho não finaliza a compra
 - Number Picker no Carrinho não foi implementado, sempre é adicionado 1 item ao carrinho ( a lógica da quantidade foi implementada inclusive no calculo do frete, mas faltou
 desenvolver o custom widget pra ficar com estilo igual ao mockup)
 - Falta placeholders com loading pra todos os banners e itens carregados do back-end
 - O caso de uso para calculo de frete está criado e rascunhado, porém não está sendo chamado no código
 

## Melhorias previstas:
 - Pode ser usado algum injetor de dependência
 - o MVVM está aplicado, mas o clean architecture pode ser melhorado.
 - Pequenos ajustes na UI para ser mais fiel aos mockups
 
