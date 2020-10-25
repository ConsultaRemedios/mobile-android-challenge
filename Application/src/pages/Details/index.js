import React, {Component} from 'react';
import { ActivityIndicator, BackHandler, View, Text, ScrollView, Alert, Image, TouchableOpacity, Dimensions } from 'react-native';
import styles from './styles';
import Rating from  '../../components/Rating'
import Icon from 'react-native-vector-icons/AntDesign';
import Icon2 from 'react-native-vector-icons/FontAwesome';
import colors from '../../assets/colors'
import {cartAdd, cartRemove, isOnCart} from '../../assets/Cart'
import {runTiming} from '../../services/animationHelper'

import Animated, {Easing} from 'react-native-reanimated';

const {
   Value, 
   event, 
   block, 
   cond, 
   eq, 
   set, 
   Clock, 
   stopClock, 
   startClock, 
   debug, 
   timing, 
   clockRunning,
   interpolate,
   Extrapolate,
   call,
   concat,
 } = Animated

class Details extends Component{
   
   constructor(props){
      super(props)
      
      this.state = {
         cartIconName: 'cart-plus',
         carIconBackColor: colors.green,
         isOnCart: false,
         item: {
            title: '...',
            price: 0,
            discount: 0,
            reviews: 0,
            rating: 0,
            stars: 0
         },
         loading: true
      }

      this.btnRotation = new Value(0)
   }

   async componentDidMount(){
      this.backHandler = BackHandler.addEventListener('hardwareBackPress',() => this.backAction())
      
      try{
         const apiCall = await fetch(`https://api-mobile-test.herokuapp.com/api/games/${this.props.route.params.id}`)
         const response = await apiCall.json()

         isOnCart(response, (isOnCart) => {
            if(isOnCart){
               this.setState({
                  cartIconName: 'cart-arrow-down',
                  carIconBackColor: colors.red1
               })
            }else{
               this.setState({
                  cartIconName: 'cart-plus',
                  carIconBackColor: colors.green
               })
            }
         })

         this.setState({
            item: response,
            loading: false
         })
      }catch(err){
         this.setState({
            loading: false
         })
         Alert.alert('','Verifique sua conexão com a internet.')

         const { goBack } = this.props.navigation;
         goBack();
         
         console.log("Error fetching data-----------", err);
      }
   }

   componentWillUnmount(){
      this.backHandler.remove()
   }

   backAction(){
      const { goBack } = this.props.navigation;
      goBack();
      return true
   }

   toggleAnimation(){

      if(this.state.isOnCart){
         this.btnRotation = runTiming(new Clock, 360, 0, 300)
      }else{
         this.btnRotation = runTiming(new Clock, 0, 360, 300)
      }

      this.setState({isOnCart: !this.state.isOnCart})
   }

   render(){
      return(
         <View style={styles.container}>
            <ScrollView style={styles.scroll} contentContainerStyle={styles.scrollContent}>
               <Image
                  source={{uri: this.state.item.image}}
                  style={styles.image}  
                  resizeMode="cover"
               />

               <View style={styles.infoView}>
                  <Text style={styles.titleText}>{this.state.item.title}</Text>
                  <View
                     style={styles.ratingView}
                  >
                     <Text style={styles.ratingText}>{this.state.item.rating}</Text>
                     <Rating style={styles.rating} rating={this.state.item.stars} size={15} color="#f7b500"/>
                     <Text style={styles.reviewsNumberText}>{this.state.item.reviews}</Text>
                     <Text style={styles.reviewsText}>reviews</Text>
                  </View>
                  <Text style={styles.oldPrice}>de R${this.state.item.price},00</Text>
                  <Text style={styles.newPrice}>R${this.state.item.price - this.state.item.discount},00</Text>
               </View>

               <View style={styles.descriptionView}>
                  <Text style={styles.descriptionText}>{this.state.item.description}</Text>
               </View>

               <Animated.View
                  style={[
                     styles.cartBtnView,
                     {
                        transform:[{rotateY: concat(this.btnRotation, 'deg')}]
                     }
                  ]}
               >
                  <TouchableOpacity
                     style={[
                        styles.cartBtn, 
                        {
                           backgroundColor: this.state.carIconBackColor
                        }
                     ]}
                     onPress={()=>{
                        isOnCart(this.state.item, (isOnCart) => {
                           if(isOnCart){
                              cartRemove(this.state.item)
                              this.setState({
                                 cartIconName: 'cart-plus',
                                 carIconBackColor: colors.green
                              })
                           }else{
                              cartAdd(this.state.item)
                              this.setState({
                                 cartIconName: 'cart-arrow-down',
                                 carIconBackColor: colors.red1
                              })
                           }   
                           this.toggleAnimation()
                        })
                     }}
                  >
                     <Icon2 name={this.state.cartIconName} size={30} color={colors.white}/>
                  </TouchableOpacity>
               </Animated.View>

               <TouchableOpacity 
                  style={styles.backBtn}
                  onPress={()=>{
                     const { goBack } = this.props.navigation;
                     goBack();
                  }}
               >
                  <Icon name="arrowleft" size={25} color={colors.white}/>
               </TouchableOpacity>
            </ScrollView>

            {
               this.state.loading ?
                  <View 
                     style={{
                        position: 'absolute',
                        top: 0,
                        left: 0,
                        height: Dimensions.get('screen').height,
                        width: Dimensions.get('screen').width,
                        justifyContent: 'center',
                        alignItems: 'center',
                        backgroundColor: 'transparent',
                        elevation: 10
                     }}
                  >
                     <ActivityIndicator animating={true} color={colors.green} size={80}/>
                  </View>
               :
                  <></>
            }

         </View>
      );
   }

}

export default Details;