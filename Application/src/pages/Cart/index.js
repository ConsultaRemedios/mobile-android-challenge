import React, {Component} from 'react';
import { View, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity, Dimensions, TouchableOpacityBase } from 'react-native';
import styles from './styles';
const {width, height} = Dimensions.get('window')
import Icon from 'react-native-vector-icons/AntDesign';
import colors from '../../assets/colors'


class Cart extends Component{
   
   constructor(props){
      super(props)
      
      this.state = {
         
      };
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
            >
               
            </FlatList>

            <View>
               
            </View>
         </View>
      );
   }

}

export default Cart;