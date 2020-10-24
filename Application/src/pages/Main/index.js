import React, {Component} from 'react';
import { View, RefreshControl, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity, Dimensions, TouchableOpacityBase } from 'react-native';
import Icon from 'react-native-vector-icons/AntDesign';
import colors from '../../assets/colors'

import SpotlightRow from '../../components/SpotlightRow'
import styles from './styles';
const {width, height} = Dimensions.get('window')
import { WebView } from 'react-native-webview';
import {runTiming} from '../../services/animationHelper'
import { cartGet } from '../../assets/Cart'

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

class Main extends Component{
   
   constructor(props){
      super(props)
      
      this.state = {
         banners: undefined,
         spotlight: undefined,
         cartAmount: 0
      }

      this.scrollY = new Value(0)

      this.searchBarX = interpolate(this.scrollY,{
         inputRange: [0, 25, 150],
         outputRange: [0, 0, -width*2],
         extrapolate: Extrapolate.CLAMP
      })
      this.searchBarOpacity =  interpolate(this.scrollY,{
         inputRange: [0, 25, 100],
         outputRange: [1, 1, 0],
         extrapolate: Extrapolate.CLAMP
      })

      this.props.navigation.addListener('focus', e => {
         cartGet((cart) => {
            this.setState({ cartAmount: cart.length })
         })
      })
   }

   async componentDidMount(){
      try{
         const apiCallBanner = await fetch("https://api-mobile-test.herokuapp.com/api/banners")
         const responseBanner = await apiCallBanner.json()

         const apiCallSpotlight = await fetch("https://api-mobile-test.herokuapp.com/api/spotlight")
         const responseSpotlight = await apiCallSpotlight.json()
         const organizedSpotlight = await this.organizeSpotlight(responseSpotlight)

         this.setState({
            banners: responseBanner,
            spotlight: organizedSpotlight   
         })
      }catch(err){
         console.log("Error fetching data-----------", err);
      }
   }

   async organizeSpotlight(spotlight){
      var organizedSpotlight = []    
      
      for (let index = 0; index < spotlight.length; index += 2) {
         if(spotlight[index+1] == null){
            organizedSpotlight.push([
               spotlight[index]
            ])
         }else{
            organizedSpotlight.push([
               spotlight[index],
               spotlight[index+1]
            ])
         }
      }

      return organizedSpotlight
   }

   toggleAnimation(isExiting){
      if(isExiting){
         this.searchBarX = runTiming(new Clock(), 0, -width, 200, ()=>{})
      }else{
         this.searchBarX = runTiming(new Clock(), -width, 0, 200, ()=>{})
      }
   }

   renderSeparator = () => {
      return (
        <View
          style={{
            width: 9
          }}
        />
      );
   };

   render(){
      return(
         <View style={styles.container}>
            <StatusBar translucent backgroundColor="transparent"/>
            
            <Animated.ScrollView 
               style={styles.mainScroll} 
               contentContainerStyle={styles.mainScrollContent}
               onScroll={
                  Animated.event(
                     [
                        {nativeEvent: {contentOffset:{y: this.scrollY}}}
                     ],
                     {useNativeDriver: true}
                  )
               }
            >
               <FlatList
                  horizontal={true}
                  showsHorizontalScrollIndicator={false}
                  style={styles.flatListBanner}
                  contentContainerStyle={styles.flatListBannerContent}
                  data={this.state.banners}
                  ItemSeparatorComponent={this.renderSeparator}
                  keyExtractor={(item, index) => JSON.stringify(index)}
                  renderItem={({item, index}) => {
                     return(
                        <TouchableOpacity 
                           onPress={()=>{
                              //Alert.alert('',`Open ${item.url}`)
                              this.props.navigation.navigate('webViewPage',{url: item.url})
                           }}
                        >
                           <Image
                              style={styles.banner}
                              source={{uri: item.image}}
                              resizeMode="cover"
                           />
                        </TouchableOpacity>
                     )
                  }}
               />

               <View style={styles.spotlightView}>
                  {
                     this.state.spotlight != undefined ?
                        this.state.spotlight.map((item, index) => {
                           return(
                              <SpotlightRow data={item} style={styles.spotlightRow} navigation={this.props.navigation}/>
                           )
                        })
                     :
                        <></>
                  }
               </View>

            </Animated.ScrollView>

            <TouchableOpacity 
               style={styles.cartBtn}
               onPress={() => {
                  this.props.navigation.navigate('cart')
               }}
            >
               <Icon name="shoppingcart" size={30} color={colors.white}/>
               <Text style={{
                  position: 'absolute',
                  width: 16,
                  height: 16,
                  right: 14,
                  top: 14,
                  backgroundColor: colors.white,
                  borderRadius: 8,
                  textAlign: 'center',
                  textAlignVertical: 'center',
                  color: colors.red1,
                  fontSize: 12
               }}>{this.state.cartAmount}</Text>
            </TouchableOpacity>

            <Animated.View 
               style={[
                  styles.searchBar,
                  {
                     transform:[{
                        translateX: this.searchBarX
                     }],
                     opacity: this.searchBarOpacity
                  }
               ]}
            >

            </Animated.View>

         </View>
      );
   }

}

export default Main;