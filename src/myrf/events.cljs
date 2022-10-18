(ns myrf.events
  (:require
   [re-frame.core :as re-frame]
   [myrf.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::update-quantity
 (fn [db [_ product-info quantity action]]
   (assoc-in db [:products product-info :quantity] (action quantity))))

(re-frame/reg-event-db
 ::reset-quantity
 (fn [db [_ product-info]]
   (assoc-in db [:products product-info :quantity] 0)))