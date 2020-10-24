import {StyleSheet, StatusBar, Dimensions} from 'react-native';
import colors from '../../assets/colors'

const {width, height} = Dimensions.get('screen')

export default styles = StyleSheet.create({
   container:{
      flex: 1,
      width: '100%'
   },
   flatList:{
      flex: 1,
      width: '100%',
      backgroundColor: colors.bg
   },
   topbar:{
      flexDirection: 'row',
      width: '100%',
      height: StatusBar.currentHeight + 70,
      backgroundColor: colors.blue
   },
   backBtn:{
      marginTop: StatusBar.currentHeight,
      height: 70,
      width: 50,
      justifyContent: 'center',
      alignItems: 'center'
   },
   topBarText:{
      marginTop: StatusBar.currentHeight,
      marginLeft: 10,
      height: 70,
      textAlignVertical: 'center',
      color: colors.white,
      fontSize: 22
   },
   bottomBar:{
      backgroundColor: colors.bgDark,
      padding: 20,
      alignItems: 'center'
   },
   productsView:{
      width: '100%',
      flexDirection: 'row',
      justifyContent: 'space-between'
   },
   shippingView:{
      width: '100%',
      flexDirection: 'row',
      justifyContent: 'space-between'
   },
   totalPriceView:{
      width: '100%',
      flexDirection: 'row',
      justifyContent: 'space-between',
      marginTop: 5
   },
   productsText:{
      color: colors.white,
      fontSize: 15
   },
   productsValueText:{
      color: colors.white,
      fontSize: 18
   },
   shippingText:{
      color: colors.white,
      fontSize: 15
   },
   shippingValueText:{
      color: colors.white,
      fontSize: 18
   },
   totalText:{
      color: colors.white,
      fontSize: 20
   },
   totalValueText:{
      color: colors.white,
      fontSize: 20
   },
   checkoutBtn:{
      backgroundColor: colors.green,
      width: '90%',
      height: 45,
      borderRadius: 5,
      alignItems: 'center',
      justifyContent: 'center',
      marginTop: 10
   },
   checkoutText:{
      color: colors.white,
      fontSize: 18
   },
   emptyCartText:{
      position: 'absolute',
      top: height/3,
      width: '100%',
      textAlign: 'center',
      color: colors.white,
      fontSize: 20
   }
});



