import React, {Component} from 'react';
import { View, Text, ScrollView, StatusBar, Alert, FlatList, Image, TouchableOpacity } from 'react-native';

import SpotlightRow from '../../components/SpotlightRow'
import styles from './styles';

class Main extends Component{
   
   constructor(props){
      super(props)
      
      this.state = {
         banners: undefined,
         spotlight: undefined
      };
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
            
            <ScrollView style={styles.mainScroll} contentContainerStyle={styles.mainScrollContent}>
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
                              Alert.alert('',`Open ${item.url}`)
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

            </ScrollView>

            <TouchableOpacity style={styles.cartBtn}>

            </TouchableOpacity>

            <View style={styles.searchBar}>

            </View>
         </View>
      );
   }

}

export default Main;