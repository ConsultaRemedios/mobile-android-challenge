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
   },
   endBackView:{
      position: 'absolute',
      top: 0,
      left: 0,
      height: Dimensions.get('screen').height,
      width: Dimensions.get('screen').width,
      backgroundColor: 'transparent',
      justifyContent: 'center',
      alignItems: 'center'
   },
   endView:{
      height: '50%',
      width: '80%',
      backgroundColor: colors.white,
      borderRadius: 10,
      elevation: 10
   },
   endTopView:{
      height: '50%',
      width: '100%',
      borderTopLeftRadius: 10,
      borderTopRightRadius: 10,
      backgroundColor: colors.green,
      justifyContent: 'center',
      alignItems: 'center'
   },
   endTopTitle:{
      color: colors.white,
      fontSize: 17,
      marginTop: 20
   },
   endBottomView:{
      flex: 1,
      padding: 15
   },
   endBottomTotalView:{
      flexDirection: 'row',
      justifyContent: 'space-between'
   },
   endBottomTotalText:{
      fontSize: 16
   },
   endBottomTotalValueView:{
      fontSize: 16
   },
   endBottomEconomyView:{
      flexDirection: 'row',
      justifyContent: 'space-between',
      marginTop: 5
   },
   endBottomEconomyText:{
      fontSize: 16
   },
   endBottomEconomyValueText:{
      fontSize: 16
   },
   beginBtn:{
      height: 60,
      width: '100%',
      justifyContent: 'center',
      alignItems: 'center',
      borderTopWidth: 1,
      borderColor: colors.gray
   },
   beginBtnText:{
      fontSize: 14,
      color: colors.bg
   },
   errorBtn:{
      height: 60,
      flex: 1,
      justifyContent: 'center',
      alignItems: 'center',
      borderTopWidth: 1,
      borderColor: colors.gray
   },
   errorBtnText:{
      fontSize: 14,
      color: colors.bg
   }
});



