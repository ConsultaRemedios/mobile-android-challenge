import React, {Component} from 'react';
import { View, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity, Dimensions, TouchableOpacityBase } from 'react-native';
import { Rating, AirbnbRating } from 'react-native-ratings';
import styles from './styles';
const {width, height} = Dimensions.get('window')

class Details extends Component{
   
   constructor(props){
      super(props)
      
      this.state = {
         
      };
   }

   render(){
      return(
         <View style={styles.container}>
            <Image
               source={{uri: this.props.route.params.data.image}}
               style={styles.image}  
               resizeMode="cover"
            />

            <View style={styles.InfoView}>
               <Text>{this.props.route.params.data.title}</Text>
               <View
                  style={styles.ratingView}
               >
                  <Text>{this.props.route.params.data.rating}</Text>
                  <AirbnbRating 
                     defaultRating={this.props.route.params.data.stars}
                     reviews={[]}
                     isDisabled={true}
                     ratingBackgroundColor='red'
                     size={20}
                  />
                  <Text>{this.props.route.params.data.reviews} reviews</Text>
               </View>
               
            </View>

            <View style={styles.DescriptionView}>

            </View>

            <TouchableOpacity
               style={styles.cartBtn}
            >

            </TouchableOpacity>

            <TouchableOpacity 
               style={styles.backBtn}
               onPress={()=>{
                  const { goBack } = this.props.navigation;
                  goBack();
               }}
            >

            </TouchableOpacity>
         </View>
      );
   }

}

export default Details;