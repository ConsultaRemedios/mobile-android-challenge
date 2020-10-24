import AsyncStorage from '@react-native-community/async-storage';

storeData = async (key, value, callback) => {
    try {
        await AsyncStorage.setItem(key, value);
        callback != undefined ? callback(value) : null
    } catch (error) {
        callback != undefined ? callback(value) : null
        // Error saving data
    }
}

retrieveData = async (key, callback) => {
    try {
        const value = await AsyncStorage.getItem(key);
        callback(value)
    } catch (error) {
        callback(null)
    }
}


module.exports = {
   storeData,
   retrieveData
}
