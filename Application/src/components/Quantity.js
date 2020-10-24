import React, {Component} from 'react';
import { StyleSheet, View, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity, Dimensions } from 'react-native';
import colors from '../assets/colors';
import Icon from 'react-native-vector-icons/AntDesign';

const {width, height} = Dimensions.get('window')

class Quantity extends Component{
   constructor(){
      super()

      this.state = {
         quantity: 0
      }

   }

   componentDidMount(){
      if(this.props.startingValue != undefined){
         this.setState({
            quantity: this.props.startingValue
         })
      }
   }

   render(){
      return(
         <View
            style={[
               styles.container,
               this.props.style
            ]}
         >
            <TouchableOpacity 
               style={[
                  styles.minusBtn, 
                  {
                     height: this.props.style.height,
                     width: this.props.style.height
                  }
               ]}
               onPress={() => {
                  if(this.state.quantity > 0){
                     if(this.props.onChange != undefined){
                        this.props.onChange(this.state.quantity-1)
                     }
                     this.setState({quantity: this.state.quantity-1})
                  }
               }}
            >
               <Icon name="minus" size={this.props.style.height * 0.5} color={colors.white}/>
            </TouchableOpacity>
            
            <Text 
               style={[
                  styles.quantityText,
                  {
                     fontSize: this.props.style.height * 0.4
                  }
               ]}
            >{this.state.quantity}</Text>

            <TouchableOpacity 
               style={[
                  styles.plusBtn, 
                  {
                     height: this.props.style.height,
                     width: this.props.style.height
                  }
               ]}
               onPress={() => {
                  if(this.props.onChange != undefined){
                     this.props.onChange(this.state.quantity+1)
                  }
                  this.setState({quantity: this.state.quantity+1})
               }}
            >
               <Icon name="plus" size={this.props.style.height * 0.5} color={colors.white}/>
            </TouchableOpacity>
         </View>
      )
   }
}

const styles = StyleSheet.create({
   container:{
      flexDirection: 'row',
      backgroundColor: 'transparent',
      borderColor: colors.gray,
      borderWidth: 2,
      borderRadius: 7,
      alignItems: 'center'
   },
   minusBtn:{
      justifyContent: 'center',
      alignItems: 'center'
   },
   quantityText:{
      flex: 1,
      height: '100%',
      textAlign: 'center',
      textAlignVertical: 'center',
      color: colors.white
   },
   plusBtn:{
      height: 70,
      width: 70,
      justifyContent: 'center',
      alignItems: 'center'
   }
});

export default Quantity

