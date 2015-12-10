(ns movies-cljs.moviecell
  (:require [goog.object :as gobj]
            [goog.string :as gstring]
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

(defn get-image-source
  [movie kind]
  (let [uri   (when (and movie (gobj/get movie "posters"))
                (gobj/getValueByKeys movie "posters" "thumbnail"))
        uri   (when (and uri kind)
                (.replace uri "tmb" kind))]
    uri))

(def MAX-VAL 200)

(defn get-style-from-score
  [score]
  (if (neg? score)
    {:color "#999999"}
    (let [normalized-score  (.round js/Math (* MAX-VAL (/ score 100)))]
      {:color (str "rgb(" (- MAX-VAL normalized-score) "," normalized-score ", 0)")})))

(defn get-text-from-score
  [score]
  (if (pos? score)
    (str score "%")
    "N/A"))

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
                   :source {:uri (get-image-source movie "det")}}]
       [mic/View {:style (:textContainer styles)}
        [mic/Text {:style (:movieTitle styles)
                   :numberOfLines 2}
         (gobj/get movie "title")]
        [mic/Text {:style (:movieYear styles)
                   :numberOfLines 1}
         (gobj/get movie "year")
         (str " " "-" " ")
         [mic/Text {:style (get-style-from-score score)}
          (str "Critics " (get-text-from-score score))]]]]]]))
