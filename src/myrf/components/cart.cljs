(ns myrf.components.cart
  (:require
   [re-frame.core :as re-frame]
   [myrf.subs :as subs]))


(defn cart-component
  "represent a cart of products"
  [product-id]
  (let [cart-quantity @(re-frame/subscribe [::subs/cart-quantity product-id])
        name @(re-frame/subscribe [::subs/name product-id])]
    [:div {:key (str name product-id)} name cart-quantity]))