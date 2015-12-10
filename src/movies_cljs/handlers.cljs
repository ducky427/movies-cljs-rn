(ns movies-cljs.handlers
  (:require
   [re-frame.core :refer [register-handler path trim-v after dispatch]]))

(register-handler
 :initialize-db
 (fn [_ [_ app-db]]
   app-db))

(register-handler
 :set-filter
 (fn [db [_ value]]
   (assoc db :filter value)))

(register-handler
 :set-data-source
 (fn [db [_ value]]
   (-> db
       (assoc :loading? false)
       (update :data-source (fn [xs]
                              (.cloneWithRows xs value))))))

(register-handler
 :start-querying
 (fn [db [_ value]]
   (assoc db
          :loading? true
          :loading-tail? false)))

(register-handler
 :dump-appstate
 (fn [db _]
   (.log js/console (prn db))
   db))
