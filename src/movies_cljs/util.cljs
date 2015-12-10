(ns movies-cljs.util
  (:require [goog.object :as gobj]))

(defn get-image-source
  [movie kind]
  (let [uri   (when (and movie (gobj/get movie "posters"))
                (gobj/getValueByKeys movie "posters" "thumbnail"))
        uri   (when (and uri kind)
                (.replace uri "tmb" kind))]
    {:uri uri}))

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
