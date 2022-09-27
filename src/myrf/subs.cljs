(ns myrf.subs
  (:require
   [re-frame.core :as re-frame]))


(re-frame/reg-sub
 ::products
 (fn [db]
   (:products db)))

