(ns ^:figwheel-load movies-cljs.ios.core
  (:require [clojure.string :as string]
            [goog.object :as gobj]
            [reagent.core :as reagent]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [movies-cljs.search :as ms]
            [movies-cljs.moviecell :as mmc]
            [movies-cljs.moviescreen :as mms]
            [movies-cljs.ios.components :as mic]
            [movies-cljs.ios.searchbar :as msb]
            [movies-cljs.handlers]
            [movies-cljs.subs]))

(def TimerMixin (js/require "react-timer-mixin/TimerMixin.js"))

(defn row-has-changed [x y]
  (let [row-1 (js->clj x :keywordize-keys true)
        row-2 (js->clj y :keywordize-keys true)]
    (not= row-1 row-2)))

(def app-db {:loading? false
             :loading-tail? false
             :filter ""
             :data-source (mic/DataSource. #js {:rowHasChanged row-has-changed})})

(def styles {:container {:flex 1
                         :backgroundColor "white"}
             :noMoviesText {:marginTop 80
                            :color "#888888"}
             :separator {:height 1
                         :backgroundColor "#eeeeee"}
             :centerText {:alignItems "center"}
             :rowSeparator {:backgroundColor "rgba(0, 0, 0, 0.1)"
                            :height 1
                            :marginLeft 4}
             :rowSeparatorHide {:opacity 0.0}})

(defn on-search-change
  [d e]
  (let [qry (.. e -nativeEvent -text toLowerCase)]
    (.clearTimeout d @ms/timeout-id)
    (swap! ms/timeout-id (fn [_]
                           (.setTimeout d
                                        (fn []
                                          (ms/search-movies qry))
                                        400)))))

(defn NoMovies
  [qry loading?]
  (let [txt  (cond
               (not (string/blank? qry)) (str "No results for " qry)
               (not loading?) "No movies found"
               :else "")]
    [mic/View {:style [(:container styles) (:centerText styles)]}
     [mic/Text {:style (:noMoviesText styles)} txt]]))

(defn render-separator
  [section row adj-row-highlighted?]
  (let [sty  (if adj-row-highlighted?
               [(:rowSeparator styles)
                (:rowSeparatorHide styles)]
               (:rowSeparator styles))]
    (reagent/as-element
     [mic/View {:key (str "SEP_" section "_" row)
                :style sty}])))

(defn select-movie
  [nav movie]
  (.push nav #js {:title (gobj/get movie "title")
                  :component mms/MovieScreen
                  :passProps movie}))

(defn render-row
  [nav movie section row highlight-row-fn]
  (reagent/as-element
   [mmc/MovieCell {:key (gobj/get movie "id")
                   :onSelect #(select-movie nav movie)
                   :onHighlight #(highlight-row-fn section row)
                   :onUnhighlight #(highlight-row-fn nil nil)
                   :movie movie}]))

(defn render-search-screen
  [d]
  (let [loading? (subscribe [:loading?])
        qry      (subscribe [:get-filter])
        d-s      (subscribe [:get-data-source])]
    (fn []
      [mic/View {:style (:container styles)}
       [msb/SearchBar @loading? (partial on-search-change d)]
       [mic/View {:style (:separator styles)}]
       (if (zero? (.getRowCount @d-s))
         [NoMovies @qry @loading?]
         [mic/ListView
          {:automaticallyAdjustContentInsets false
           :keyboardDismissMode "on-drag"
           :keyboardShouldPersistTaps true
           :showsVerticalScrollIndicator true
           :renderSeparator #(render-separator %1 %2 %3)
           :dataSource @d-s
           :renderRow #(render-row
                        (gobj/get (nth (gobj/get (.-props d) "argv") 1) "navigator")
                        %1 %2 %3 %4)}])])))

(def SearchScreen-comp (reagent/create-class {:render (fn [d]
                                                        [render-search-screen d])
                                              :component-did-mount (fn []
                                                                     (ms/search-movies ""))
                                              :mixins #js [TimerMixin]
                                              :display-name "Search Screen"}))

(defn movies-app
  []
  [mic/NavigatorIOS {:style (:container styles)
                     :initialRoute {:title "Movies"
                                    :component SearchScreen-comp}}])

(defn mount-root []
  (reagent/render [movies-app] 1))

(defn ^:export init []
  (dispatch-sync [:initialize-db app-db])
  (.registerRunnable mic/App-Registry "MoviesCljs" #(mount-root)))
