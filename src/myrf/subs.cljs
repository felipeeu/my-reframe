(ns myrf.subs
  (:require
   [re-frame.core :refer [reg-sub]]
   [myrf.utils.helpers :refer [filter-by-title]]))

(reg-sub
 ::title
 (fn [db [_ product-id]]
   (get-in db [:products product-id :title])))

(reg-sub
 ::price
 (fn [db [_ product-id]]
   (get-in db [:products product-id :price])))

(reg-sub
 ::quantity
 (fn [db [_ product-id]]
   (get-in db [:products product-id :quantity])))

(reg-sub
 ::image
 (fn [db [_ product-id]]
   (get-in db [:products product-id :image])))

(reg-sub
 ::rate
 (fn [db [_ product-id]]
   (get-in db [:products product-id :rating :rate])))

(reg-sub
 ::description
 (fn [db [_ product-id]]
   (get-in db [:products product-id :description])))

(reg-sub
 ::list-products-ids
 (fn [db]
   (keys (:products db))))

(reg-sub
 ::cart-quantity
 (fn [db [_ product-id]]
   (get-in db [:cart product-id :quantity])))

(reg-sub
 ::list-cart-products-ids
 (fn [db]
   (keys (:cart db))))

(reg-sub
 ::active-page
 (fn [db _]
   (:active-page db)))

(reg-sub
 ::param-id
 (fn [db]
   (:selected db)))

(reg-sub
 ::filtered-data
 (fn [db]
   (keys (filter-by-title db (:filtered db)))))

(reg-sub
 ::loading
 (fn [db]
   (:loading db)))

(reg-sub
 ::theme
 (fn [db]
   (:theme db)))