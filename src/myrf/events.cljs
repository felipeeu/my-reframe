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
 ::update-cart-quantity
 (fn [db [_ product-info quantity action]]
   (assoc-in db [:cart product-info :quantity] (action quantity))))

(re-frame/reg-event-db
 ::reset-quantity
 (fn [db [_ product-info]]
   (assoc-in db [:products product-info :quantity] 0)))

(re-frame/reg-event-db
 ::register-cart-status
 (fn [db [_ product-info status]]
   (assoc-in db [:products product-info :cart-added] status)))

(re-frame/reg-event-fx
 ::add-to-cart
 (fn [{:keys [db]} [_ product-info quantity]]
   (let [cart-db  {product-info {:quantity quantity}}]
     (if (> quantity 0)
       {:db  (assoc db :cart
                    (conj  (:cart db) cart-db))
        :fx [[:dispatch [::register-cart-status product-info true]]]} nil))))

(re-frame/reg-event-fx
 ::remove-from-cart
 (fn [{:keys [db]} [_ product-info quantity]]
   (let [cart-db  {product-info {:quantity quantity}}]
     (if (< quantity 0)
       {:db  (dissoc db :cart
                     (conj  (:cart db) cart-db))
        :fx [[:dispatch [::register-cart-status product-info false]]]}
       nil))))

(re-frame/reg-event-fx
 ::set-active-page
 (fn [{:keys [db]} [_ {:keys [page slug]}]]
   (let [set-page (assoc db :active-page page)]
     (case page
       :home {:db set-page}
       :cart {:db set-page}
       :login {:db set-page}
       :product  {:db (assoc set-page :active-product slug)}))))

(re-frame/reg-event-db
 ::select-product
 (fn [db [_ product-info]]
   (assoc-in db [:selected] product-info)))