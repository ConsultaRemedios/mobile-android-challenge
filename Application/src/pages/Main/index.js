import React, {Component} from 'react';
import { ActivityIndicator, BackHandler, Keyboard, View, RefreshControl, Text, StatusBar, Alert, FlatList, Image, TouchableOpacity, Dimensions } from 'react-native';
import Icon from 'react-native-vector-icons/AntDesign';
import colors from '../../assets/colors'
import SpotlightRow from '../../components/SpotlightRow'
import styles from './styles';
const {width, height} = Dimensions.get('window')
import {runTiming} from '../../services/animationHelper'
import { cartGetTotalQuantity } from '../../assets/Cart'

import Animated, {Easing} from 'react-native-reanimated';
import { TextInput } from 'react-native-gesture-handler';

const {
   Value, 
   block,
   Clock,
   call
 } = Animated

class Main extends Component{
   
   constructor(props){
      super(props)
      
      this.state = {
         banners: undefined,
         spotlight: undefined,
         cartAmount: 0,
         isSearchBarExiting: false,
         isSearchResultExiting: false,
         searchResult: [],
         loading: true
      }

      this.searchInputRef

      this.scrollY = new Value(0)

      this.searchBarX = new Value(0)
      this.searchBarOpacity =  new Value(1)

      this.searchResultViewX = new Value(-height - 100)

      this.highest = new Value(0)
      this.old = new Value(0)

      this.props.navigation.addListener('focus', e => {
         cartGetTotalQuantity((quantity) => {
            this.setState({ cartAmount: quantity})
         })
      })
   }

   componentDidMount(){
      this.backHandler = BackHandler.addEventListener('hardwareBackPress',() => this.backAction())

      this.fetchData()
   }

   async fetchData(){
      this.setState({
         loading: true 
      })
      try{
         const apiCallBanner = await fetch("https://api-mobile-test.herokuapp.com/api/banners")
         const responseBanner = await apiCallBanner.json()

         const apiCallSpotlight = await fetch("https://api-mobile-test.herokuapp.com/api/spotlight")
         const responseSpotlight = await apiCallSpotlight.json()
         const organizedSpotlight = await this.organizeSpotlight(responseSpotlight)

         this.setState({
            banners: responseBanner,
            spotlight: organizedSpotlight,
            loading: false 
         })
      }catch(err){
         this.setState({
            loading: false
         })

         Alert.alert('','Verifique sua conexão com a internet.')
         
         console.log("Error fetching data-----------", err);
      }
   }

   componentWillUnmount(){
      this.backHandler.remove()
   }

   backAction(){
      if (!this.state.isSearchResultExiting) {
         Alert.alert('',"Deseja sair?",
            [
               {text: 'NÃO'},
               {text: 'SIM',onPress: () => { 
                  BackHandler.exitApp();
               }}
            ],
            {cancelable: true}
         )
      }else{
         this.toggleSeachResultAnimation()
      }

      return true
   }

   async searchGames(text){
      try{   
         const apiCall = await fetch(`https://api-mobile-test.herokuapp.com/api/games/search?term=${text}`)
         const response = await apiCall.json()

         this.setState({searchResult: response})
      }catch(err){
         Alert.alert('','Verifique sua conexão com a internet.')
         
         console.log("Error fetching data-----------", err);
         this.setState({searchResult: []})
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

   toggleSeachResultAnimation(){
      if(!this.state.isSearchResultExiting){
         this.searchResultViewX = runTiming(new Clock(), -height - 100, 0, 400)
      }else{
         this.searchResultViewX = runTiming(new Clock(), 0, -height - 100, 400)
         this.searchInputRef.blur()
         Keyboard.dismiss()
      }

      this.setState({isSearchResultExiting: !this.state.isSearchResultExiting})
   }

   toggleSeachBarAnimation(){
      if(!this.state.isSearchBarExiting){
         this.searchBarX = runTiming(new Clock(), 0, -width, 200)
         this.searchBarOpacity = runTiming(new Clock(), 1, 0, 200)
      }else{
         this.searchBarX = runTiming(new Clock(), -width, 0, 200)
         this.searchBarOpacity = runTiming(new Clock(), 0, 1, 200)
      }

      this.setState({isSearchBarExiting: !this.state.isSearchBarExiting})
   }

   renderSeparator = () => {
      return (
        <View
          style={{
            width: 9
          }}
        />
      );
   }

   renderSeparatorVertical = () => {
      return (
        <View
            style={{
               width: '100%',
               height: 0,
               borderColor: colors.lightGray,
               borderTopWidth: 1
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
               refreshControl={
                  <RefreshControl
                     refreshing={this.state.loading}
                     colors={[colors.green]}
                     onRefresh={() => this.fetchData()}
                  />
               }
               onScroll={
                  Animated.event(
                     [
                        {nativeEvent: {contentOffset:{y: y => block(
                           call([y], y => {
                              if(y > height*0.04 && !this.state.isSearchBarExiting){
                                 this.toggleSeachBarAnimation()
                              }else if(y < height*0.04 && this.state.isSearchBarExiting){
                                 this.toggleSeachBarAnimation()
                              }
                           }),
                        )}}}
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
                              <SpotlightRow key={JSON.stringify(index)} data={item} style={styles.spotlightRow} navigation={this.props.navigation}/>
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
                  styles.searchResultView,
                  {
                     transform:[{translateY: this.searchResultViewX}]
                  }
               ]}
            >
               <FlatList
                  style={styles.searchResultList}
                  data={this.state.searchResult}
                  keyExtractor={(item, index) => JSON.stringify(index)}
                  ItemSeparatorComponent={this.renderSeparatorVertical}
                  renderItem={({item, index}) => {
                     return(
                        <TouchableOpacity 
                           style={styles.searchItemView}
                           onPress={() => {
                              this.toggleSeachResultAnimation()
                              this.props.navigation.navigate('details',{id: item.id})
                           }}
                        >
                           <Text style={styles.searchItemTitleText}>{item.title}</Text>
                           <Text style={styles.searchItemPriceText}>R${item.price - item.discount},00</Text>
                        </TouchableOpacity>
                     )
                  }}
               />
            </Animated.View>

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
               {
                  this.state.isSearchResultExiting ?
                     <TouchableOpacity
                        style={styles.searchBarBackBtn}
                        onPress={()=>{
                           this.toggleSeachResultAnimation()
                        }}
                     >
                        <Icon name="arrowleft" size={20} color={colors.bg}/>
                     </TouchableOpacity>
                  :
                     <></>
               }

               <TextInput 
                  placeholder="Pesquisar"
                  style={styles.searchBarInput}
                  onTouchStart={()=>{
                     if(!this.state.isSearchResultExiting){
                        this.toggleSeachResultAnimation()
                     }
                  }}
                  ref={ref => this.searchInputRef = ref}
                  onChangeText={(text) => this.searchGames(text)}
               />

               <View style={styles.searchIconView}>
                  <Icon name="search1" size={20} color={colors.bg}/>
               </View>
            </Animated.View>

            {
               this.state.loading ?
                  <View 
                     style={{
                        position: 'absolute',
                        top: 0,
                        left: 0,
                        height: Dimensions.get('screen').height,
                        width: Dimensions.get('screen').width,
                        justifyContent: 'center',
                        alignItems: 'center',
                        backgroundColor: 'transparent',
                        elevation: 10
                     }}
                  >
                     <ActivityIndicator animating={true} color={colors.green} size={80}/>
                  </View>
               :
                  <></>
            }

         </View>
      );
   }

}

export default Main;