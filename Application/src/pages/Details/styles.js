import {StyleSheet, StatusBar, Dimensions} from 'react-native';
import colors from '../../assets/colors'

const {width, height} = Dimensions.get('screen')

export default styles = StyleSheet.create({
   container:{
      flex: 1
   },
   scroll:{
      height: '100%',
      width: '100%',
      backgroundColor: colors.bg,
   },
   scrollContent:{
      flexDirection: 'column',
      alignItems: 'center'
   },
   image:{
      width: width,
      height: height / 3 
   },
   infoView:{
      //height: height / 4,
      width: width,
      backgroundColor: colors.blue,
      paddingLeft: 17,
      paddingRight: 17,
      paddingBottom: 27
   },
   descriptionView:{
      flex: 1,
      width: width,
      padding: 15
   },
   descriptionText:{
      color: colors.white,
      fontSize: 17,
      lineHeight: 23
   },
   cartBtnView:{
      position: 'absolute',
      top: height / 3  - 30,
      right: 20,
      width: 60,
      height: 60,
      elevation: 10,
      justifyContent: 'center',
      alignItems: 'center'
   },
   cartBtn:{
      width: '100%',
      height: '100%',
      borderRadius: 30,
      backgroundColor: colors.green,
      justifyContent: 'center',
      alignItems: 'center'
   },
   backBtn:{
      position: 'absolute',
      left: 10,
      top: StatusBar.currentHeight + 10,
      width: 50,
      height: 50,
      alignItems: 'center',
      justifyContent: 'center'
   },
   ratingView:{
      flexDirection: 'row',
      alignItems: 'flex-end',
      marginTop: 7
   },
   titleText:{
      color: colors.white,
      fontSize: 22,
      marginTop: 30 
   },
   ratingText:{
      color: colors.white,
      fontSize: 15
   },
   rating:{
      height: 19,
      marginLeft: 7
   },
   reviewsNumberText:{
      color: colors.white,
      fontSize: 15,
      marginLeft: 7
   },
   reviewsText:{
      color: colors.white,
      fontSize: 14,
      marginLeft: 3
   },
   oldPrice:{
      color: colors.white,
      fontSize: 16,
      marginTop: 10,
      textDecorationLine: 'line-through'
   },
   newPrice:{
      color: colors.white,
      fontSize: 30
   }
});



