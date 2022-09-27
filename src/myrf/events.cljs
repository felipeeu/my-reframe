(ns myrf.events
  (:require
   [re-frame.core :as re-frame]
   [myrf.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(defn decrement-by-id
  [product id]
  (if (= id (:id product))
    (if (< 0 (:quantity product))
      (update-in product [:quantity] dec)
      product)
    product))

(re-frame/reg-event-fx
 ::decrement-number
 (fn [{:keys [db]} [_ id]]
   (let [products (map #(decrement-by-id % id) (:products db))]
     {:db (assoc-in db [:products] products)})))

(defn increment-by-id
  [product id]
  (if (= id (:id product))
    (if (not (= (:quantity product) (:inventory product)))
      (update-in product [:quantity] inc)
      product)
    product))

(re-frame/reg-event-fx
 ::increment-number
 (fn [{:keys [db]} [_ id]]
   (let [products (map #(increment-by-id % id) (:products db))]
     {:db (assoc-in db [:products] products)})))
