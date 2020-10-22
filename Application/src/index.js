
import React from 'react';
import { Text } from 'react-native'

import Main from './pages/Main';

import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

const Stack = createStackNavigator()

function MainNavigator() {

   return (
      <NavigationContainer>
         <Stack.Navigator initialRouteName="main" headerMode="none">
            <Stack.Screen name="main" component={Main} options={{headerShown: false}}/>
         </Stack.Navigator>
      </NavigationContainer>
   );
}

export default MainNavigator;