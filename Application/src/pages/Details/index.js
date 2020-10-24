import React, {Component} from 'react';
import { View, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity, Dimensions, TouchableOpacityBase } from 'react-native';
import styles from './styles';
import Rating from  '../../components/Rating'
const {width, height} = Dimensions.get('window')
import Icon from 'react-native-vector-icons/AntDesign';
import Icon2 from 'react-native-vector-icons/FontAwesome';
import colors from '../../assets/colors'
import {cartAdd, cartRemove, cartGet, isOnCart} from '../../assets/Cart'
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
         isOnCart: false
      }

      this.btnRotation = new Value(0)

      isOnCart(this.props.route.params.data, (isOnCart) => {
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
                  source={{uri: this.props.route.params.data.image}}
                  style={styles.image}  
                  resizeMode="cover"
               />

               <View style={styles.infoView}>
                  <Text style={styles.titleText}>{this.props.route.params.data.title}</Text>
                  <View
                     style={styles.ratingView}
                  >
                     <Text style={styles.ratingText}>{this.props.route.params.data.rating}</Text>
                     <Rating style={styles.rating} rating={this.props.route.params.data.stars} size={15} color="#f7b500"/>
                     <Text style={styles.reviewsNumberText}>{this.props.route.params.data.reviews}</Text>
                     <Text style={styles.reviewsText}>reviews</Text>
                  </View>
                  <Text style={styles.oldPrice}>de R${this.props.route.params.data.price},00</Text>
                  <Text style={styles.newPrice}>R${this.props.route.params.data.price - this.props.route.params.data.discount},00</Text>
               </View>

               <View style={styles.descriptionView}>
                  <Text style={styles.descriptionText}>{this.props.route.params.data.description}</Text>
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
                        isOnCart(this.props.route.params.data, (isOnCart) => {
                           if(isOnCart){
                              cartRemove(this.props.route.params.data)
                              this.setState({
                                 cartIconName: 'cart-plus',
                                 carIconBackColor: colors.green
                              })
                           }else{
                              cartAdd(this.props.route.params.data)
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
         </View>
      );
   }

}

export default Details;