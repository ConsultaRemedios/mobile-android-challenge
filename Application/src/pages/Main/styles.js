import {StyleSheet, StatusBar, Dimensions} from 'react-native';
import colors from '../../assets/colors'

const {width, height} = Dimensions.get('screen')

const SEARCH_BAR_HEIGHT = 50

export default styles = StyleSheet.create({
   container:{
      flex: 1,
      flexDirection: 'column',
      backgroundColor: colors.bg,
      alignItems: 'center'
   },
   mainScroll:{
      flex: 1,
      width: '100%'
   },
   mainScrollContent:{
      paddingTop: StatusBar.currentHeight + 10 + SEARCH_BAR_HEIGHT + 30
   },
   flatListBanner:{
      height: 160,
      width: '100%'
   },
   flatListBannerContent:{
      paddingLeft: width*0.05,
      paddingRight: width*0.05
   },
   banner:{
      height: '100%',
      width: width*0.9,
      borderRadius: 10
   },
   spotlightView:{
      paddingTop: 20,
      paddingLeft: 20,
      paddingRight: 20
   },
   spotlightRow:{
      flexDirection: 'row',
   },
   searchBar:{
      flexDirection: 'row',
      position: 'absolute',
      top: StatusBar.currentHeight + 10,
      backgroundColor: colors.white,
      width: '92%',
      height: SEARCH_BAR_HEIGHT,
      elevation: 10
   },
   cartBtn:{
      position: 'absolute',
      width: 60,
      height: 60,
      bottom: 15,
      right: 15,
      borderRadius: 30,
      backgroundColor: colors.red1,
      justifyContent: 'center',
      alignItems: 'center',
      elevation: 10
   },
   searchBarBackBtn:{
      height: '100%',
      width: SEARCH_BAR_HEIGHT,
      justifyContent: 'center',
      alignItems: 'center'
   },
   searchBarInput:{
      flex: 1,
      height: '100%',
      paddingLeft: 20,
      paddingRight: 20
   },
   searchIconView:{
      height: '100%',
      width: SEARCH_BAR_HEIGHT,
      justifyContent: 'center',
      alignItems: 'center'
   },
   searchResultView:{
      position: 'absolute',
      top: 0,
      left: 0,
      height: '100%',
      width: width,
      backgroundColor: colors.white,
      padding: 15,
      paddingTop: StatusBar.currentHeight + 10 + SEARCH_BAR_HEIGHT + 15
   },
   searchResultList:{
      width: '100%',
      height: '100%'
   },
   searchItemView:{
      flexDirection: 'row',
      paddingTop: 15,
      paddingBottom: 15,
      justifyContent: 'space-between'
   },
   searchItemTitleText:{
      flex:1,
      color: colors.darkGray,
      fontSize: 18
   },
   searchItemPriceText:{
      color: colors.blue,
      fontSize: 16,
      paddingLeft: 10,
      paddingRight: 10
   }
});



