(ns movies-cljs.search
  (:require [clojure.string :as string]
            [goog.object :as gobj]
            [re-frame.core :refer [dispatch]]))

(def API-URL "http://api.rottentomatoes.com/api/public/v1.0/")
(def API-KEY "7waqfqbprs7pajbz28mqf6vz")

(def timeout-id (atom nil))

(defn get-url
  [qry page]
  (if (string/blank? qry)
    (str API-URL "lists/movies/in_theaters.json?apikey=" API-KEY "&page_limit=20&page=" page)
    (str API-URL "movies.json?apikey=" API-KEY "&q=" (js/encodeURIComponent qry) "&page_limit=20&page=" page)))

(defn search-movies
  [qry]
  (dispatch [:set-filter qry])
  (dispatch [:start-querying])
  (reset! timeout-id nil)
  (let [url (get-url qry 1)]
    (js/console.log url)
    (-> (js/fetch url)
        (.then (fn [resp]
                 (.json resp)))
        (.then (fn [res]
                 (dispatch [:set-data-source (gobj/get res "movies")])))
        (.done))))
