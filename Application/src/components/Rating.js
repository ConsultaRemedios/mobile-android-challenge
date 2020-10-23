import React, {Component} from 'react';
import { StyleSheet, View, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity, Dimensions } from 'react-native';
import colors from '../assets/colors';
import Icon from 'react-native-vector-icons/FontAwesome';

const {width, height} = Dimensions.get('window')

class Rating extends Component{
   constructor(){
      super()

   }

   componentDidMount(){

   }

   render(){
      return(
         <View style={[styles.container, this.props.style]}>
            <Icon name="star" size={this.props.size} color={this.props.rating >= 1 ? this.props.color : colors.gray} />
            <Icon name="star" size={this.props.size} color={this.props.rating >= 2 ? this.props.color : colors.gray} style={{marginLeft: 1}}/>
            <Icon name="star" size={this.props.size} color={this.props.rating >= 3 ? this.props.color : colors.gray} style={{marginLeft: 1}}/>
            <Icon name="star" size={this.props.size} color={this.props.rating >= 4 ? this.props.color : colors.gray} style={{marginLeft: 1}}/>
            <Icon name="star" size={this.props.size} color={this.props.rating >= 5 ? this.props.color : colors.gray} style={{marginLeft: 1}}/>
         </View>
      )
   }
}

const styles = StyleSheet.create({
   container:{
      flexDirection: 'row'
   }
});

export default Rating

