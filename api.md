
# API
Para acessar API de teste use os dados:
- Base url: `https://api-mobile-test.herokuapp.com/api/`

### Serviços
##### [GET]/banners
Retorna a lista de banners
```
curl -X GET https://api-mobile-test.herokuapp.com/api/banners
```

##### [GET]/spotlight
Retorna a lista de games que deve ser apresentado na home

```
curl -X GET https://api-mobile-test.herokuapp.com/api/spotlight
```

##### [GET]/game/:id
Retorna o detalhamento de um game especifico

```
curl -X GET https://api-mobile-test.herokuapp.com/api/games/1
```


##### [GET]/game/search?term=a
Retorna a lista de games que tem em parte de seu título o termo informado

```
curl -X GET https://api-mobile-test.herokuapp.com/api/games/search?term=a
```

##### [POST]/checkout
Finaliza a compra. Para fins de teste, esse serviço não precisa receber nenhum valor, entretanto, existe uma pequena chance do serviço retornar `ERROR 400`

```
curl -X POST https://api-mobile-test.herokuapp.com/api/checkout
```
