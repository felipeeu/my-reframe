(ns myrf.components.product
  (:require  [re-frame.core :as re-frame]
             [myrf.subs :as subs]
             [myrf.events :as events]))

(defn product
  "represent a single product"
  [product-id]
  (let [name @(re-frame/subscribe [::subs/name product-id])
        price @(re-frame/subscribe [::subs/price product-id])
        quantity  @(re-frame/subscribe [::subs/quantity product-id])]
    [:div {:key (str name product-id) :class "box"}
     [:img {:src "no-image.png" :style {:width 80}}]
     [:p  name]
     [:p (str "$"  price)]
     [:button {:on-click #(re-frame.core/dispatch [::events/show-modal product-id quantity])} "choose product"]]))
