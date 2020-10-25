import React, {Component} from 'react';
import { BackHandler, View, TouchableOpacity, Dimensions } from 'react-native';
import { WebView } from 'react-native-webview';
import Icon from 'react-native-vector-icons/AntDesign';
import colors from '../../assets/colors'

import styles from './styles'

class WebViewPage extends Component{
   
   constructor(props){
      super(props)
      
      this.state = {
         
      };
   }

   componentDidMount(){
      this.backHandler = BackHandler.addEventListener('hardwareBackPress',() => this.backAction())
   }

   componentWillUnmount(){
      this.backHandler.remove()
   }

   backAction(){
      const { goBack } = this.props.navigation;
      goBack();
      return true
   }

   render(){
      return(
         <View style={styles.container}>
            <WebView
               source={{uri: this.props.route.params.url}}
               style={styles.webView}
            />

            <TouchableOpacity 
               style={styles.backButton}
               onPress={()=> {
                  const { goBack } = this.props.navigation;
                  goBack();
               }}
            >
               <Icon name="arrowleft" size={25} color={colors.white}/>
            </TouchableOpacity>
         </View>
      );
   }

}

export default WebViewPage;