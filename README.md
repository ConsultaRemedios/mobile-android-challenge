### Informações gerais sobre o Projeto

-  O projeto foi elaborado utilizando o padrão arquitetural MVI (Model-View-Intent).

- O projeto do teste foi criado com base de um template que criei no meu Github, a intenção do template é deixar toda a estrutura pronta para ganhar tempo na criação de um novo projeto e evitar o processo demorado e repetitiva quando tiver que criar um projeto do zero.

- para desenvolver, utilizei o esquema de criar uma branch develop, e em seguida criar uma branch com o prefixo: feature, fix.
Após a conclusão tarefa, realizar o merge na branch develop e no fim fazer merge da develop com a master. (Embora que ás vezes eu esquecia de seguir o padrão por conta do tempo, mas geralmente é assim que desenvolvo)

### Motivos para escolher este padrão arquitetural
 - Fluxo de dados unidirecional e cíclico.
 - Um estado consistente durante o ciclo de vida das Views.
 - Modelos imutáveis ​​que fornecem comportamento confiável e segurança de threads em aplicativos grandes.


### Linguagem utilizada:
   - Kotlin


###  Bibliotecas utilizadas na implementação:
   - Mobius Spotify (Uma biblioteca criada pela equipe do Spoty com uma estrutura reativa funcional para gerenciar a evolução do estado e efeitos colaterais, com complementos para conectar-se às UIs do Android e ao RxJava Observables. Ele enfatiza a separação de preocupações, a testabilidade e o isolamento de partes com estado do código.)
   - rxbinding3 (Para converter os widgets em observables)
   - Dagger 2 (Para injentar as dependências)
   - ViewModels (Trabalhar a lógica de negócio)
   - Jetpack navigation
   - Room database
   - Databinding (Para evitar boilerplate e manter um código mais limpo)
   - Safe Args
   - Retrofit2: moshi-converter, adapter-rxjava2
   - RxJava2
   - Glide
   - AndroidX
   - RxJava (RxKotlin)
   - Material Design 2.0
   - CounterFab (Botão flutuante com a contagem dos itens na tela home)
   - PersistentSearch (Para facilitar a criação do search na tela de pesquisa dos jogos)
   - Why Not! Image Carousel (Utilizei para criar a parte dos banner na home screen)

### Bibliotecas utilizadas para testes:
   - Espresso
   - JUnit
   - MockWebServer (Para simular a api criando um servidor local)
