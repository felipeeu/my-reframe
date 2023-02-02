(ns myrf.components.cart
  (:require
   [myrf.subs :as subs]
   [re-frame.core :as re-frame]
   [myrf.components.quantity-selector :refer [change-quantity-buttons]]))


(defn cart-component
  "represent the products there is in a cart"
  [product-id]
  (let [cart-quantity @(re-frame/subscribe [::subs/cart-quantity product-id])
        name @(re-frame/subscribe [::subs/name product-id])
        price @(re-frame/subscribe [::subs/price product-id])
        total (* cart-quantity price)]
    [:tr {:key (str name product-id)}
     [:td
      [:img {:src "no-image.png"
             :style {:width "80px"}}]]
     [:td
      [:p {:class "is-family-primary"} name]]
     [:td
      [:p {:class "is-family-primary"} price]]
     [:td
      [:p {:class "is-family-primary"} [change-quantity-buttons cart-quantity product-id "cart-quantity"]]]
     [:td
      [:p {:class "is-family-primary"} total]]]))