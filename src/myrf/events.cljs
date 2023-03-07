(ns myrf.events
  (:require [ajax.core :as ajax]
            [day8.re-frame.http-fx]
            [myrf.db :as db]
            [myrf.utils.helpers :refer [normalize-db]]
            [re-frame.core :as re-frame]))


(re-frame/reg-event-fx
 ::initialize-db
 (fn [{:keys [_]} [_ _]]
   {:db db/default-db
    :fx [[:dispatch [::http-get]]]}))


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
       :register {:db set-page}
       :login {:db set-page}
       :product  {:db (assoc set-page :active-product slug)}))))

(re-frame/reg-event-db
 ::select-product
 (fn [db [_ product-info]]
   (assoc-in db [:selected] product-info)))

(re-frame/reg-event-db
 ::filter-by-name
 (fn [db [_ title]]
   (assoc-in db [:filtered] title)))


(re-frame/reg-event-fx
 ::http-get
 (fn [{:keys [db]} [_ _]]
   {:db (assoc-in db [:loading] true)
    :http-xhrio {:method          :get
                 :uri             "https://fakestoreapi.com/products/"
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::success-get-result]
                 :on-failure      [::failure-get-result]}}))

(re-frame/reg-event-db
 ::success-get-result
 (fn [db [_ result]]
   (-> db
       (assoc-in  [:products]
                  (normalize-db result))
       (assoc-in [:loading] false))))


(re-frame/reg-event-db
 ::failure-get-result
 (fn [db [_ result]]
   (assoc-in db [:failure] result)))