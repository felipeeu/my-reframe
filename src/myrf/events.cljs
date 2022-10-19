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

(re-frame/reg-event-db
 ::register-cart-added
 (fn [db [_ product-info]]
   (assoc-in db [:products product-info :cart-added] true)))

(re-frame/reg-event-fx
 ::add-to-cart
 (fn [{:keys [db]} [_ product-info quantity]]
   (let [cart-db  {product-info {:quantity quantity}}]
     (if (> quantity 0)
       {:db  (assoc db :cart
                    (conj  (:cart db) cart-db))
        :fx [[:dispatch [::register-cart-added product-info]]]}
       nil))))