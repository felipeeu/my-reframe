(ns myrf.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db [_ product-id]]
   (get-in db [:products product-id :name])))

(re-frame/reg-sub
 ::price
 (fn [db [_ product-id]]
   (get-in db [:products product-id :price])))

(re-frame/reg-sub
 ::quantity
 (fn [db [_ product-id]]
   (get-in db [:products product-id :quantity])))

(re-frame/reg-sub
 ::inventory
 (fn [db [_ product-id]]
   (get-in db [:products product-id :inventory])))

(re-frame/reg-sub
 ::list-products-ids
 (fn [db]
   (keys (:products db))))

(re-frame/reg-sub
 ::cart-quantity
 (fn [db [_ product-id]]
   (get-in db [:cart product-id :quantity])))

(re-frame/reg-sub
 ::list-cart-products-ids
 (fn [db]
   (keys (:cart db))))

(re-frame/reg-sub
 ::modal-opened
 (fn [db]
   (get-in db [:modal-opened :status])))

(re-frame/reg-sub
 ::modal-opened-id
 (fn [db]
   (get-in db [:modal-opened :id])))