import React, {Component} from 'react';
import { StyleSheet, View, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity, Dimensions } from 'react-native';
import colors from '../assets/colors';
import Quantity from './Quantity'
import Icon from 'react-native-vector-icons/FontAwesome';

const {width, height} = Dimensions.get('window')

import {runTiming} from '../services/animationHelper'

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

class CartItem extends Component{
   constructor(){
      super()

      this.itemX = new Value(0)
      this.itemOpacity = new Value(1)
   }

   animate(callback){
      this.itemX = runTiming(new Clock(), 0, width - 20, 200)
      this.itemOpacity = runTiming(new Clock(), 1, 0, 200, ()=>{
         this.itemX = new Value(0)
         this.itemOpacity = new Value(1)
         callback()
      })
      this.forceUpdate()
   }

   render(){
      return(
         <Animated.View
            style={[
               styles.container,
               {
                  transform:[{translateX: this.itemX}],
                  height: this.props.itemHeight,
                  opacity: this.itemOpacity
               }
            ]}
         >
            <Image 
               source={{uri: this.props.data.item.image}} 
               style={[
                  styles.image,
                  {
                     height: this.props.itemHeight*0.8,
                     width: this.props.itemHeight*0.8
                  }
               ]}
            />
            <View style={{flex: 1, paddingLeft: 10, paddingRight: 10}}>
               <Text style={styles.titleText}>{this.props.data.item.title}</Text>
               <View style={{flexDirection: 'row', flex: 1, width: '100%', alignItems: 'center'}}>
                  <Quantity 
                     startingValue={this.props.data.quantidade}
                     style={{
                        height: 38, 
                        width: 110
                     }}
                     onZeroReached={() => {
                        Alert.alert('','Deseja remover este item?', [
                           {text: 'sim', onPress: ()=>{
                              this.animate(()=>{
                                 this.props.onRemovePress()
                              })
                           }},
                           {text: 'não'}
                        ])
                     }}
                     onChange={(value) => {   
                        this.props.onQuantityChange(value)
                     }}
                  />

                  <TouchableOpacity
                     style={styles.removeBtn}
                     onPress={() => {
                        Alert.alert('','Deseja remover este item?', [
                           {text: 'sim', onPress: ()=>{
                              this.animate(()=>{
                                 this.props.onRemovePress()
                              })
                           }},
                           {text: 'não'}
                        ])
                     }}
                  >
                     <Icon name="trash-o" size={25} color={colors.white}/>
                  </TouchableOpacity>
                  <View
                     style={styles.priceView}
                  >
                     <Text style={styles.oldPrice}>de R${this.props.data.item.price},00</Text>
                     <Text style={styles.newPrice}>R${this.props.data.item.price - this.props.data.item.discount},00</Text>
                  </View>
               </View>
            </View>
         </Animated.View>
      )
   }
}

const styles = StyleSheet.create({
   container:{
      width: '100%',
      padding: 10,
      flexDirection: 'row',
      alignItems: 'center'
   },
   image:{
      borderRadius: 7
   },
   titleText:{
      flex: 1,
      color: colors.white,
      fontSize: 16,
      textAlignVertical: 'center'
   },
   removeBtn:{
      height: '100%',
      width: 30,
      justifyContent: 'center',
      alignItems: 'center',
      marginLeft: 7
   },
   priceView:{
      flex: 1,
      alignItems: 'flex-end'
   },
   oldPrice:{
      color: colors.white,
      fontSize: 13,
      textDecorationLine: 'line-through'
   },
   newPrice:{
      color: colors.blue,
      fontSize: 18
   }
});

export default CartItem

