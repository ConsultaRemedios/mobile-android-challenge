import {retrieveData, storeData} from '../services/persistHelper'
import {Alert} from 'react-native'
import { call } from 'react-native-reanimated'

cartAdd = (item) => {
   retrieveData('cart', (cart) => {
      var cartJson = JSON.parse(cart)
      var entrou = false

      const newItem = {
         'item': item,
         'quantidade': 1
      }

      if(cartJson == null){
         cartJson = []
      }

      cartJson.map((item2, index) => {
         if(item.id == item2.item.id){
            entrou = true
         }
      })

      if(!entrou){
         cartJson.push(newItem)
         storeData('cart', JSON.stringify(cartJson))
      }
   })
}

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

cartGet = (callback) => {
   retrieveData('cart', (cart) => {
      var cartJson = JSON.parse(cart)
      callback(cartJson)
   })
}

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

module.exports = {
   cartAdd,
   cartRemove,
   cartGet,
   isOnCart
}