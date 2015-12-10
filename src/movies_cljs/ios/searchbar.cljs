(ns movies-cljs.ios.searchbar
  (:require [movies-cljs.ios.components :as mic]))

(def styles {:searchBar {:marginTop 64
                         :padding 3
                         :paddingLeft 8
                         :flexDirection "row"
                         :alignItems "center"}
             :searchBarInput {:fontSize 15
                              :flex 1
                              :height 30}
             :spinner {:width 30}})

(defn SearchBar
  [loading? on-search-change]
  [mic/View {:style (:searchBar styles)}
   [mic/TextInput
    {:autoCapitalize "none"
     :autoCorrect false
     :onChange on-search-change
     :placeholder "Search a movie..."
     :style (:searchBarInput styles)}]
   [mic/ActivityIndicatorIOS
    {:animating loading?
     :style (:spinner styles)}]])
