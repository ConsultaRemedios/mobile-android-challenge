import {retrieveData, storeData} from '../services/persistHelper'

//adds an item in the cart
cartAdd = (item) => {
   retrieveData('cart', (cart) => {
      var cartJson = JSON.parse(cart)
      var entered = false

      const newItem = {
         'item': item,
         'quantidade': 1
      }

      if(cartJson == null){
         cartJson = []
      }

      cartJson.map((item2, index) => {
         if(item.id == item2.item.id){
            entered = true
         }
      })

      if(!entered){
         cartJson.push(newItem)
         storeData('cart', JSON.stringify(cartJson))
      }
   })
}

//sets the quantity of an item
cartSetQuantity = (index, quantity) => {
   retrieveData('cart', (cart) => {
      var cartJson = JSON.parse(cart)
      
      cartJson[index].quantidade = quantity

      storeData('cart', JSON.stringify(cartJson))
   })
}

//removes an item from the cart
cartRemove = (item) => {
   retrieveData('cart', (cart) => {
      var cartJson = JSON.parse(cart)
      var index = -1

      cartJson.map((item2, index2) => {
         if(item.id == item2.item.id){
            index = index2
         }
      })

      if(index >= 0){
         cartJson.splice(index, 1)
         storeData('cart', JSON.stringify(cartJson))
      }
   })
}

//get the whole cart
cartGet = (callback) => {
   retrieveData('cart', (cart) => {
      var cartJson = JSON.parse(cart)
      callback(cartJson)
   })
}

//check if an item is on the cart
isOnCart = (item, callback) => {
   retrieveData('cart', (cart) => {
      var cartJson = JSON.parse(cart)
      var entrou = false

      if(cartJson == null){
         callback(false)
      }else{
         cartJson.map((item2, index) => {
            if(item.id == item2.item.id){
               entrou = true
            }
         })

         if(entrou){
            callback(true)
         }else{
            callback(false)
         }
      }
   })
}

//gets the total quantity of items in the cart
cartGetTotalQuantity = (callback) =>{
   retrieveData('cart', (cart) => {
      var cartJson = JSON.parse(cart)
      var quantity = 0

      cartJson.map((item, index) => {
         quantity += item.quantidade
      })

      callback(quantity)
   })
}

//resets the cart
clearCart = () => {
   storeData('cart', "[]")
}

module.exports = {
   cartAdd,
   cartRemove,
   cartGet,
   isOnCart,
   cartSetQuantity,
   cartGetTotalQuantity,
   clearCart
}