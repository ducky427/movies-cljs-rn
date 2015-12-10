(ns movies-cljs.moviecell
  (:require [goog.object :as gobj]
            [movies-cljs.util :as util]
            [movies-cljs.ios.components :as mic]))

(def styles {:row {:alignItems "center"
                   :backgroundColor "white"
                   :flexDirection "row"
                   :padding 5}
             :cellImage {:backgroundColor "#dddddd"
                         :height 93
                         :marginRight 10
                         :width 60}
             :textContainer {:flex 1}
             :movieTitle {:flex 1
                          :fontSize 16
                          :fontWeight "500"
                          :marginBottom 2}
             :movieYear {:color "#999999"
                         :fontSize 12}})

(defn MovieCell
  [{:keys [movie onSelect onHighlight onUnhighlight] :as props}]
  (let [score (gobj/getValueByKeys movie "ratings" "critics_score")]
    [mic/View
     [mic/TouchableHighlight
      {:onPress onSelect
       :onShowUnderlay onHighlight
       :onHideUnderlay onUnhighlight}
      [mic/View {:style (:row styles)}
       [mic/Image {:style (:cellImage styles)
                   :source (util/get-image-source movie "det")}]
       [mic/View {:style (:textContainer styles)}
        [mic/Text {:style (:movieTitle styles)
                   :numberOfLines 2}
         (gobj/get movie "title")]
        [mic/Text {:style (:movieYear styles)
                   :numberOfLines 1}
         (gobj/get movie "year")
         (str " " "-" " ")
         [mic/Text {:style (util/get-style-from-score score)}
          (str "Critics " (util/get-text-from-score score))]]]]]]))
