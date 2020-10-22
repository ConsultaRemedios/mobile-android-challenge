import {StyleSheet, StatusBar, Dimensions} from 'react-native';
import colors from '../../assets/colors'

const {width, height} = Dimensions.get('screen')

export default styles = StyleSheet.create({
   container:{
      flex: 1,
      flexDirection: 'column',
      backgroundColor: colors.bg,
      alignItems: 'center'
   },
   image:{
      width: width,
      height: 300 
   },
   InfoView:{
      height: 200,
      width: width,
      backgroundColor: colors.blue
   },
   DescriptionView:{
      flex: 1,
      width: width
   },
   cartBtn:{
      position: 'absolute',
      top: 300 - 30,
      right: 20,
      width: 60,
      height: 60,
      borderRadius: 30,
      backgroundColor: colors.green,
      elevation: 10
   },
   backBtn:{
      position: 'absolute',
      left: 10,
      top: StatusBar.currentHeight + 10,
      width: 50,
      height: 50,
      backgroundColor: 'red'
   },
   ratingView:{
      flexDirection: 'row'
   }
});



