## Benhur - Consulta Remedios Mobile Challenge


## Build Variants

- Release: Build de produção.
- Debug: Build de desenvolvimento.

## Módulos

Existem 5 módulos separados em dois tipos:
*Clique para ver mais detalhes dos módulos*

#### Libraries:
- [Base] - Colors, Utils, Styles que são compartilhados pelos módulos
- [Network] - Responsável pela comunicação com as APIs

#### Features:
- [Banners] - Renderiza os banners da Home
- [Cards] - Renderiza os cards da Home
- [Checkout] - Renderiza os botões de carrinho e a tela final de compra
- [Detail] - Renderiza a tela de detalhes
- [Search] - Renderiza a barra de procura e a tela de sugestões

## Arquitetura

O aplicativo foi feito seguindo o MVP (Model - View - Presenter).

### Teste

o CartReposiroty esta coberto com testes unitários.

###Pendências
 
 - Criar testes de interface nas telas
 - Criar teste de integração
 - Criar testes unitários em todos os presenters
 