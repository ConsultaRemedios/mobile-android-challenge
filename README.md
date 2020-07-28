## Consulta Remedios Mobile Challenge

O teste consiste em construir uma aplicação nativa Android de um pseudo ecommerce de games.

### Arquitetura, ferramentas e bibliotecas utilizadas no app
- Nesse app utilizei arquitetura **MVVM**
- Para realizar chamadas a API utilizei como ferramenta o **Retrofit**
- Como auxílio na injeção de dependências utilizei a ferramenta **Dagger 2**
- Para salvar os dados no banco de dados usei a biblioteca **Room**
- Para operações assíncronas com a API e o DB utilizei o padrão de **coroutines**
- Demais componentes e ferramentas do jetpack utilizados nesse projeto foram:
	. LiveData
	. ViewModel
	. RecyclerView
	. Os testes unitários foram realizados utilizando Junit e coroutines test do android

### Dificuldades
Por ter sido bastante features em pouco tempo para ser desenvolvido, foi um desafio manter o código com padrões, totalmente organizado e escalável, por falta de tempo não consegui realizar testes instrumentais, e daria para ter feito mais testes unitários testando melhor as ViewModels e os Repositories. E na necessidade de escrever muito código em pouco tempo, os fluxos do git não ficaram tão legais, daria para separar melhor as features e ser mais preciso nos commits. fora isso foi um teste legal onde nenhuma feature me travou por muito tempo. 

