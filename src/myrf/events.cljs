(ns myrf.events
  (:require
   [re-frame.core :as re-frame]
   [myrf.db :as db]))

(def mocked-data
  {:name "generic"
   :quantity 0
   :inventory 10
   :image "no-image.png"
   :id "1"
   :price 20
   :cart-added false})

(defn increase-mocked-data
  [db data max]
  (into (:products db)
        (for [i (range 5 max)] [(str i) data])))

(re-frame/reg-event-fx
 ::initialize-db
 (fn [{:keys [_]} [_ _]]
   {:db db/default-db
    :fx [[:dispatch [::update-db]]]}))

(re-frame/reg-event-db
 ::update-db
 (fn [db [_ _]]
   (assoc-in db [:products] (increase-mocked-data db/default-db mocked-data 20))))


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

(re-frame/reg-event-fx
 ::register-cart-status
 (fn [{:keys [db]} [_ product-info status]]
   {:db (assoc-in db [:products product-info :cart-added] status)
    ;; :fx [[:dispatch [::set-active-page :cart ""]]]
    }))

(re-frame/reg-event-fx
 ::add-to-cart
 (fn [{:keys [db]} [_ product-info quantity]]
   (let [cart-db  {product-info {:quantity quantity}}]
     (if (> quantity 0)
       {:db  (assoc db :cart
                    (conj  (:cart db) cart-db))
        :fx [[:dispatch [::register-cart-status product-info true]]]} nil))))


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