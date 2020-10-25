import React, {Component} from 'react';
import { BackHandler, View, Text, Alert, FlatList, TouchableOpacity, Dimensions } from 'react-native';
import styles from './styles';
const {width, height} = Dimensions.get('window')
import Icon from 'react-native-vector-icons/AntDesign';
import colors from '../../assets/colors'
import { cartRemove, cartGet, cartSetQuantity, clearCart} from '../../assets/Cart'
import CartItem from '../../components/CartItem'
import _ from 'lodash'

import {runTiming} from '../../services/animationHelper'

import Animated, {Easing} from 'react-native-reanimated';

const {
   Value,
   Clock
 } = Animated

class Cart extends Component{
   
   constructor(props){
      super(props)
      
      this.state = {
         cart: null,
         isEndViewVisible: false,
         isEndViewErrorVisible: false
      }

      this.endViewX = new Value(-width - 100)
      this.endViewErrorX = new Value(-width - 100)

      cartGet((cart2) => {
         this.setState({cart: cart2})
      })
   }

   toggleEndViewAnimation(){
      if(this.state.isEndViewVisible){
         this.endViewX = runTiming(new Clock, 0, -width - 100, 200)
      }else{
         this.endViewX = runTiming(new Clock, -width - 100, 0, 200)
      }

      this.setState({ isEndViewVisible: !this.state.isEndViewVisible })
   }

   toggleEndViewErrorAnimation(){
      if(this.state.isEndViewErrorVisible){
         this.endViewErrorX = runTiming(new Clock, 0, -width - 100, 200)
      }else{
         this.endViewErrorX = runTiming(new Clock, -width - 100, 0, 200)
      }

      this.setState({ isEndViewErrorVisible: !this.state.isEndViewErrorVisible })
   }

   componentDidMount(){
      this.backHandler = BackHandler.addEventListener('hardwareBackPress',() => this.backAction())
   }

   componentWillUnmount(){
      this.backHandler.remove()
   }

   backAction(){
      const { goBack } = this.props.navigation;
      goBack();
      return true
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

   getDiscounts(){
      var discount = 0

      if(this.state.cart != null){
         this.state.cart.map((item, index) => {
            discount += item.item.discount
         })
      }
      
      return discount
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

   async checkout(){
      try{
         const apiCall = await fetch("https://api-mobile-test.herokuapp.com/api/checkout",{method: 'POST'})

         if(apiCall.status == 202){
            this.toggleEndViewAnimation()
            
            clearCart()
         }else{
            this.toggleEndViewErrorAnimation()
         }

         this.setState({
            loading: false 
         })
      }catch(err){
         this.setState({
            loading: false 
         })

         this.toggleEndViewErrorAnimation()

         console.log("Error fetching data-----------", err);
      }
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
                        onQuantityChange={(quantity) => {
                           var tempCart = _.cloneDeep(this.state.cart)
                           tempCart[index].quantidade = quantity

                           cartSetQuantity(index, quantity)

                           this.setState({cart: tempCart})
                        }}
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
                  style={[
                     styles.checkoutBtn,
                     {
                        backgroundColor: this.state.cart == null ? (colors.gray) : (this.state.cart.length > 0 ? colors.green : colors.gray)
                     }
                  ]}
                  onPress={() => {
                     if(this.state.cart != null && this.state.cart.length > 0){
                        Alert.alert('',"Deseja finalizar a compra?",
                           [
                              {text: 'NÃO'},
                              {text: 'SIM',onPress: () => { 
                                 this.checkout()
                              }}
                           ],
                           {cancelable: true}
                        )
                     }
                  }}
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

            <Animated.View 
               style={[
                  styles.endBackView,
                  {
                     transform: [{translateX: this.endViewX}]
                  }
               ]}
            >
               <View style={styles.endView}>
                  <View style={styles.endTopView}>
                     <Icon name="checkcircleo" color={colors.white} size={50}/>
                     <Text style={styles.endTopTitle}>Compra finalizada com sucesso!</Text>
                  </View>

                  <View style={styles.endBottomView}>
                     <View style={styles.endBottomTotalView}>
                        <Text style={styles.endBottomTotalText}>Total</Text>
                        <Text style={styles.endBottomTotalValueView}>R$ {this.getPrice() + this.getShippingPrice()},00</Text>
                     </View>
                     <View style={styles.endBottomEconomyView}>
                        <Text style={styles.endBottomEconomyText}>Você economizou</Text>
                        <Text style={styles.endBottomEconomyValueText}>R$ {this.getDiscounts()},00</Text>
                     </View>
                  </View>

                  <TouchableOpacity 
                     style={styles.beginBtn}
                     onPress={() => {
                        const { goBack } = this.props.navigation;
                        goBack();
                     }}
                  >
                     <Text style={styles.beginBtnText}>Ir para o início</Text>
                  </TouchableOpacity>
               </View>
            </Animated.View>

            <Animated.View 
               style={[
                  styles.endBackView,
                  {
                     transform: [{translateX: this.endViewErrorX}]
                  }
               ]}
            >
               <View style={styles.endView}>
                  <View style={[styles.endTopView, {backgroundColor: colors.red1}]}>
                     <Icon name="closecircleo" color={colors.white} size={50}/>
                     <Text style={styles.endTopTitle}>Compra não finalizada</Text>
                  </View>

                  <View style={styles.endBottomView}>
                     <Text style={styles.endBottomTotalText}>Ocorreu algum erro na conexão com a internet.</Text>
                  </View>

                  <View 
                     style={{
                        flexDirection: 'row',
                        width: '100%',
                        alignItems: 'center'
                     }}
                  >
                     <TouchableOpacity 
                        style={styles.errorBtn}
                        onPress={() => {
                           const { goBack } = this.props.navigation;
                           goBack();
                        }}
                     >
                        <Text style={styles.errorBtnText}>Ir para o início</Text>
                     </TouchableOpacity>

                     <View style={{borderLeftColor: colors.gray, borderLeftWidth: 1, height: '80%'}}/>

                     <TouchableOpacity 
                        style={styles.errorBtn}
                        onPress={() => {
                           this.toggleEndViewErrorAnimation()
                           this.checkout()
                        }}
                     >
                        <Text style={styles.errorBtnText}>Tentar novamente</Text>
                     </TouchableOpacity>
                  </View>
               </View>
            </Animated.View>
            
         </View>
      );
   }

}

export default Cart;