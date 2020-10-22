import React, {Component} from 'react';
import { StyleSheet, View, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity } from 'react-native';

import SpotlightItem from '../components/SpotlightItem'

class SpotlightRow extends Component{
   constructor(){
      super()

   }

   render(){
      return(
         <View style={this.props.style}>
            <View style={{flex: 1}}>
               <SpotlightItem data={this.props.data[0]} navigation={this.props.navigation}/>
            </View>
            {
               this.props.data[1] != null ?
                  <View style={styles.item}>
                     <SpotlightItem data={this.props.data[1]} navigation={this.props.navigation}/>
                  </View>
               :
                  <View style={styles.item}/>
            }
         </View>
      )
   }
}

const styles = StyleSheet.create({
   item:{
      flex: 1
   }
});

export default SpotlightRow

