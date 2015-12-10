(ns movies-cljs.ios.components
  (:require [reagent.core :as r]))

(set! js/React (js/require "react-native/Libraries/react-native/react-native.js"))

(def App-Registry (.-AppRegistry js/React))
(def Text (r/adapt-react-class (.-Text js/React)))
(def View (r/adapt-react-class (.-View js/React)))
(def Image (r/adapt-react-class (.-Image js/React)))
(def Touchable-Highlight (r/adapt-react-class (.-TouchableHighlight js/React)))
