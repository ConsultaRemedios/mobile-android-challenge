
import React from 'react';
import { Text } from 'react-native'

import Main from './pages/Main';
import WebViewPage from './pages/WebViewPage';

import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

const Stack = createStackNavigator()

function MainNavigator() {

   return (
      <NavigationContainer>
         <Stack.Navigator initialRouteName="main" headerMode="none">
            <Stack.Screen name="main" component={Main} options={{headerShown: false}}/>
            <Stack.Screen name="webViewPage" component={WebViewPage} options={{headerShown: false}}/>
         </Stack.Navigator>
      </NavigationContainer>
   );
}

export default MainNavigator;