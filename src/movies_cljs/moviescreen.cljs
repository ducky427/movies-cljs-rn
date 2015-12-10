(ns movies-cljs.moviescreen
  (:require [reagent.core :as reagent]
            [goog.object :as gobj]
            [goog.string :as gstring]
            [movies-cljs.util :as util]
            [movies-cljs.ios.components :as mic]))

(def styles {:contentContainer {:padding 10}
             :mainSection {:flexDirection "row"}
             :detailsImage {:width 134
                            :height 200
                            :backgroundColor "#eaeaea"
                            :marginRight 10}
             :rightPane {:justifyContent "space-between"
                         :flex 1}
             :movieTitle {:flex 1
                          :fontSize 16
                          :fontWeight "500"}
             :mpaaWrapper {:alignSelf "flex-start"
                           :borderColor "black"
                           :borderWidth 1
                           :paddingHorizontal 3
                           :marginVertical 5}
             :mpaaText {:fontFamily "Palatino"
                        :fontSize 13
                        :fontWeight "500"}
             :separator {:backgroundColor "rgba(0, 0, 0, 0.1)"
                         :height (/ 1 (.get mic/PixelRatio))
                         :marginVertical 10}
             :rating {:marginTop 10}
             :ratingTitle {:fontSize 14}
             :ratingValue {:fontSize 28
                           :fontWeight "500"}
             :castTitle {:fontWeight "500"
                         :marginBottom 3}
             :castActor {:marginLeft 2}})

(defn Ratings
  [ratings]
  (let [c-score (gobj/get ratings "critics_score")
        a-score (gobj/get ratings "audience_score")]
    [mic/View
     [mic/View {:style (:rating styles)}
      [mic/Text {:style (:ratingTitle styles)} "Critics:"]
      [mic/Text {:style [(:ratingValue styles) (util/get-style-from-score c-score)]}
       (util/get-text-from-score c-score)]]
     [mic/View {:style (:rating styles)}
      [mic/Text {:style (:ratingTitle styles)} "Audience:"]
      [mic/Text {:style [(:ratingValue styles) (util/get-style-from-score a-score)]}
       (util/get-text-from-score a-score)]]]))

(defn Cast
  [cast]
  [mic/View
   [mic/Text {:style (:castTitle styles)} "Actors"]
   (for [c cast
         :let [n  (gobj/get c "name")]]
     ^{:key n} [mic/Text {:style (:castActor styles)} "- " n])])

(defn render
  [props]
  (let [movie (clj->js props)]
    [mic/ScrollView {:contentContainerStyle (:contentContainer styles)}
     [mic/View {:style (:mainSection styles)}
      [mic/Image {:source (util/get-image-source movie "det")
                  :style (:detailsImage styles)}]
      [mic/View {:style (:rightPane styles)}
       [mic/Text {:style (:movieTitle styles)} (gobj/get movie "title")]
       [mic/Text (gobj/get movie "year")]
       [mic/View {:style (:mpaaWrapper styles)}
        [mic/Text {:style (:mpaaText styles)} (gobj/get movie "mpaa_rating")]]
       [Ratings (gobj/get movie "ratings")]]]
     [mic/View {:style (:separator styles)}]
     [mic/Text (gobj/get movie "synopsis")]
     [mic/View {:style (:separator styles)}]
     [Cast (gobj/get movie "abridged_cast")]]))

(def MovieScreen
  (reagent/reactify-component render))
