import React, {Component} from 'react';
import { StyleSheet, View, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity, Dimensions } from 'react-native';
import colors from '../assets/colors';

const {width, height} = Dimensions.get('window')

class SpotlightItem extends Component{
   constructor(){
      super()

   }

   componentDidMount(){

   }

   render(){
      return(
         <TouchableOpacity 
            style={styles.container}
            onPress={()=>{
               this.props.navigation.navigate('details',{data: this.props.data})
            }}
         >
            <Image source={{uri: this.props.data.image}} style={styles.image} resizeMode="cover"/>
            <Text style={styles.publisherText}>{this.props.data.publisher}</Text>
            <Text style={styles.titleText}>{this.props.data.title}</Text>
            <Text style={styles.oldPriceText}>de R${this.props.data.price},00</Text>
            <Text style={styles.newPriceText}>R${this.props.data.price - this.props.data.discount},00</Text>
         </TouchableOpacity>
      )
   }
}

const styles = StyleSheet.create({
   container:{
      padding: 10
   },
   image:{
      width: (width/2) - 40,
      height: (width/2) - 40,
      alignSelf: 'center',
      borderRadius: 7
   },
   publisherText:{
      fontSize: 11,
      color: colors.white,
      marginTop: 5
   },
   titleText:{
      fontSize: 14,
      color: colors.white,
      marginTop: 5
   },
   oldPriceText:{
      fontSize: 11,
      color: colors.white,
      marginTop: 5
   },
   newPriceText:{
      fontSize: 18,
      color: colors.blue
   }
});

export default SpotlightItem

