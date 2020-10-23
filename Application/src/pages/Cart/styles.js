import {StyleSheet, StatusBar, Dimensions} from 'react-native';
import colors from '../../assets/colors'

const {width, height} = Dimensions.get('screen')

export default styles = StyleSheet.create({
   container:{
      flex: 1
   },
   flatList:{
      height: '100%',
      width: '100%',
      backgroundColor: colors.bg,
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
   }
});



