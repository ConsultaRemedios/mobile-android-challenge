import React, {Component} from 'react';
import { View, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity, Dimensions, TouchableOpacityBase } from 'react-native';

import SpotlightRow from '../../components/SpotlightRow'
import styles from './styles';
const {width, height} = Dimensions.get('window')
import { WebView } from 'react-native-webview';

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
   concat
 } = Animated

class Main extends Component{
   
   constructor(props){
      super(props)
      
      this.state = {
         banners: undefined,
         spotlight: undefined
      };

      this.scrollY = new Value(0)
      this.searchBarY = interpolate(this.scrollY,{
         inputRange: [0,150],
         outputRange: [-0, -width*2],
         extrapolate: Extrapolate.CLAMP
      })
      this.searchBarOpacity =  interpolate(this.scrollY,{
         inputRange: [0,70],
         outputRange: [1, 0],
         extrapolate: Extrapolate.CLAMP
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
      
      spotlight.splice(5,1)
      
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
                              <SpotlightRow data={item} style={styles.spotlightRow}/>
                           )
                        })
                     :
                        <></>
                  }
               </View>

            </Animated.ScrollView>

            <TouchableOpacity style={styles.cartBtn}>

            </TouchableOpacity>

            <Animated.View 
               style={[
                  styles.searchBar,
                  {
                     transform:[{
                        translateX: this.searchBarY
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