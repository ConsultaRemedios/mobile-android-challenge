import {StyleSheet, StatusBar, Dimensions} from 'react-native';
import colors from '../../assets/colors'

const {width, height} = Dimensions.get('screen')

export default styles = StyleSheet.create({
   container:{
      flex: 1,
      paddingTop: StatusBar.currentHeight,
      backgroundColor: colors.bg,
   },
   webView:{
      height: '100%',
      width: '100%'
   },
   backButton:{
      position: 'absolute',
      left: 10,
      top: StatusBar.currentHeight + 10,
      width: 50,
      height: 50,
      backgroundColor: 'red'
   }
});



