import React, {Component} from 'react';
import { View, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity, Dimensions, TouchableOpacityBase } from 'react-native';
import styles from './styles';
const {width, height} = Dimensions.get('window')
import Icon from 'react-native-vector-icons/AntDesign';
import colors from '../../assets/colors'
import {cartAdd, cartRemove, cartGet, isOnCart} from '../../assets/Cart'
import CartItem from '../../components/CartItem'
import _ from 'lodash'

class Cart extends Component{
   
   constructor(props){
      super(props)
      
      this.state = {
         cart: null
      };

      cartGet((cart2) => {
         this.setState({cart: cart2})
      })
   }

   getTotalCount(){
      var count = 0

      if(this.state.cart != null){
         this.state.cart.map((item, index) => {
            count += item.quantidade
         })
      }

      return count
   }

   getPrice(){
      var price = 0

      if(this.state.cart != null){
         this.state.cart.map((item, index) => {
            price += item.quantidade * (item.item.price - item.item.discount)
         })
      }

      return price
   }

   getShippingPrice(){
      if(this.getPrice() > 250){
         return 0
      }

      var shipping = 0
      if(this.state.cart != null){
         this.state.cart.map((item, index) => {
            shipping += item.quantidade * 10
         })
      }
      
      return shipping
   }

   render(){
      return(
         <View style={styles.container}>
            <View style={styles.topbar}>
               <TouchableOpacity
                  style={styles.backBtn}
                  onPress={()=>{
                     const { goBack } = this.props.navigation;
                     goBack();
                  }}
               >
                  <Icon name="arrowleft" size={25} color={colors.white}/>
               </TouchableOpacity>
               <Text style={styles.topBarText}>Carrinho</Text>
            </View>


            <FlatList 
               style={styles.flatList}
               data={this.state.cart}
               keyExtractor={(item, index) => JSON.stringify(index)}
               renderItem={({item, index}) => {
                  return(
                     <CartItem 
                        data={item} 
                        itemHeight={120} 
                        onRemovePress={() => {
                           var tempCart = _.cloneDeep(this.state.cart)
                           tempCart.splice(index, 1)

                           cartRemove(item.item)
                           this.setState({cart: tempCart})
                        }}
                     />
                  )
               }}
            />

            <View style={styles.bottomBar}>
               <View style={styles.productsView}>
                  <Text style={styles.productsText}>Produtos ({this.getTotalCount()})</Text>
                  <Text style={styles.productsValueText}>R$ {this.getPrice()},00</Text>
               </View>

               <View style={styles.shippingView}>
                  <Text style={styles.shippingText}>Frete</Text>
                  <Text style={styles.shippingValueText}>{this.getShippingPrice() == 0 ? "Grátis" : "R$ " + this.getShippingPrice() + ",00"}</Text>
               </View>

               <View style={styles.totalPriceView}>
                  <Text style={styles.totalText}>TOTAL</Text>
                  <Text style={styles.totalValueText}>R$ {this.getPrice() + this.getShippingPrice()},00</Text>
               </View>

               <TouchableOpacity
                  style={styles.checkoutBtn}
               >
                  <Text style={styles.checkoutText}>Finalizar compra</Text>
               </TouchableOpacity>
            </View>

            {
               this.state.cart == null || this.state.cart.length == 0 ?
                  <Text style={styles.emptyCartText}>Carrinho vazio</Text>
               :
                  <></>
            }
            
         </View>
      );
   }

}

export default Cart;