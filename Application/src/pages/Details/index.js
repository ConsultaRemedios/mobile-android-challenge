import React, {Component} from 'react';
import { View, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity, Dimensions, TouchableOpacityBase } from 'react-native';
import styles from './styles';
import Rating from  '../../components/Rating'
const {width, height} = Dimensions.get('window')
import Icon from 'react-native-vector-icons/AntDesign';
import colors from '../../assets/colors'

class Details extends Component{
   
   constructor(props){
      super(props)
      
      this.state = {
         
      };
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

               <TouchableOpacity
                  style={styles.cartBtn}
               >
                  <Icon name="shoppingcart" size={30} color={colors.white}/>
               </TouchableOpacity>

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