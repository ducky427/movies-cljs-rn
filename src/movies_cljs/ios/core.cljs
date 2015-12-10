(ns ^:figwheel-load movies-cljs.ios.core
  (:require-macros [env.require-img :refer [require-img]])
  (:require [reagent.core :as reagent]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [movies-cljs.ios.components :as mic]
            [movies-cljs.handlers]
            [movies-cljs.subs]))

(def logo-img (require-img "./images/cljs.png"))

(defn widget []
  (let [greeting (subscribe [:get-greeting])]
    (fn []
      [mic/View {:style {:flexDirection "column" :margin 40 :alignItems "center"}}
       [mic/Text {:style {:fontSize 30 :fontWeight "100" :marginBottom 20 :textAlign "center"}} @greeting]
       [mic/Image {:source logo-img
               :style  {:width 80 :height 80 :marginBottom 30}}]
       [mic/Touchable-Highlight {:style {:backgroundColor "#999" :padding 10 :borderRadius 5}}
        [mic/Text {:style {:color "white" :textAlign "center" :fontWeight "bold"}} "press me"]]])))

(defn mount-root []
  (reagent/render [widget] 1))

(defn ^:export init []
  (dispatch-sync [:initialize-db])
  (.registerRunnable mic/App-Registry "MoviesCljs" #(mount-root)))
