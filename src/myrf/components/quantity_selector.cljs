(ns myrf.components.quantity-selector
  (:require
   [re-frame.core :as re-frame]
   [myrf.subs :as subs]
   [myrf.events :as events]))


(defn quantity-component
  [product-id quantity]
  (let [inventory @(re-frame/subscribe [::subs/inventory product-id])]
    [:div
     [:button {:on-click #(if  (< quantity inventory) (re-frame.core/dispatch [::events/update-quantity product-id  quantity inc]) nil) :class "button is-primary"} "+"]
     [:span quantity]
     [:button {:on-click #(if (> quantity 0) (re-frame.core/dispatch [::events/update-quantity product-id quantity dec]) nil) :class "button is-primary"} "-"]
     [:button {:on-click #(re-frame.core/dispatch [::events/reset-quantity product-id])} "empty"]]))