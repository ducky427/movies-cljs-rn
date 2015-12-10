(ns movies-cljs.ios.components
  (:require [reagent.core :as r]))

(set! js/React (js/require "react-native/Libraries/react-native/react-native.js"))

(def App-Registry (.-AppRegistry js/React))
(def Text (r/adapt-react-class (.-Text js/React)))
(def View (r/adapt-react-class (.-View js/React)))
(def Image (r/adapt-react-class (.-Image js/React)))
(def TouchableHighlight (r/adapt-react-class (.-TouchableHighlight js/React)))
(def NavigatorIOS (r/adapt-react-class (.-NavigatorIOS js/React)))
(def StyleSheet (r/adapt-react-class (.-StyleSheet js/React)))
(def ScrollView (r/adapt-react-class (.-ScrollView js/React)))
(def ActivityIndicatorIOS (r/adapt-react-class (.-ActivityIndicatorIOS js/React)))
(def TextInput (r/adapt-react-class (.-TextInput js/React)))
(def ListView (r/adapt-react-class (.-ListView js/React)))

(def DataSource js/React.ListView.DataSource)
(def PixelRatio (.-PixelRatio js/React))
