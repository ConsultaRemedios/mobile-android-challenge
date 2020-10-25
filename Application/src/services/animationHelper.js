import Animated, {Easing, useCode} from 'react-native-reanimated';

var functionCalled = false

const {
   Value, 
   event, 
   block, 
   not,
   cond, 
   eq, 
   set, 
   Clock, 
   stopClock, 
   startClock, 
   debug, 
   timing, 
   clockRunning,
   interpolate,
   Extrapolate,
   concat,
   call
 } = Animated

const runTiming = (clock, value, dest, duration, callback) => {

   functionCalled = false

   const state = {
      finished: new Value(0),
      position: new Value(0),
      time: new Value(0),
      frameTime: new Value(0)
   };
 
   const config = {
     duration: duration,
     toValue: new Value(0),
     easing: Easing.inOut(Easing.ease)
   };

   return block([
      cond(
         clockRunning(clock), 0, 
         [
            set(state.finished, 0),
            set(state.time, 0),
            set(state.position, value),
            set(state.frameTime, 0),
            set(config.toValue, dest),
            startClock(clock)
         ]
      ),
      timing(clock, state, config),
      cond(
         state.finished, 
         block(
            call([],()=>{
               if(!functionCalled && callback != undefined && callback != null){
                  callback()
                  functionCalled = true
               }
            }),
            debug('stop clock', stopClock(clock))
         )
      ),
      state.position
   ]);
}

module.exports = {
   runTiming
}