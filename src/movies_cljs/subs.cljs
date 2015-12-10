(ns movies-cljs.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [register-sub]]))

(register-sub
 :get-filter
 (fn [db _]
   (reaction
    (get @db :filter))))

(register-sub
 :get-data-source
 (fn [db _]
   (reaction
    (get @db :data-source))))

(register-sub
 :loading?
 (fn [db _]
   (reaction
    (get @db :loading?))))
