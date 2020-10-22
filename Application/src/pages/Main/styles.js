import {StyleSheet, StatusBar, Dimensions} from 'react-native';
import colors from '../../assets/colors'

const {width, height} = Dimensions.get('window')

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
      position: 'absolute',
      top: StatusBar.currentHeight + 10,
      backgroundColor: colors.white,
      width: '92%',
      height: SEARCH_BAR_HEIGHT
   },
   cartBtn:{
      position: 'absolute',
      width: 50,
      height: 50,
      bottom: 15,
      right: 15,
      borderRadius: 25,
      backgroundColor: colors.red1
   }
});



