(ns myrf.components.cart
  (:require
   [myrf.subs :as subs]
   [re-frame.core :as re-frame]
   [myrf.components.quantity-selector :refer [change-quantity-buttons]]
   [myrf.utils.price :refer [format-price]]))


(defn cart-component
  "Represent the products there is in a cart"
  [product-id text-alignment]
  (let [cart-quantity @(re-frame/subscribe [::subs/cart-quantity product-id])
        title @(re-frame/subscribe [::subs/title product-id])
        price @(re-frame/subscribe [::subs/price product-id])
        image @(re-frame/subscribe [::subs/image product-id])
        total (* cart-quantity price)]
    [:tr {:key (str title product-id)}
     [:td
      [:img {:src image
             :style {:width "80px"}}]]
     [:td
      [:p {:class text-alignment} title]]
     [:td
      [:p {:class text-alignment} (format-price price)]]
     [:td
      [:div [change-quantity-buttons cart-quantity product-id "cart-quantity"]]]
     [:td
      [:p {:class text-alignment} (format-price total)]]]))

(defn cart-table
  "Table with products on cart information"
  [cart-product-ids]
  (let [table-head-alignment "text-center"]
    [:table {:class "bg-white"}
     [:thead
      [:tr
       [:th [:p {:class table-head-alignment} "Product"]]
       [:th  [:p {:class table-head-alignment} "Name"]]
       [:th [:p {:class table-head-alignment} "Price"]]
       [:th  [:p {:class table-head-alignment} "Quantity"]]
       [:th  [:p {:class table-head-alignment} "Total"]]]]
     [:tbody
      (doall (map  #(cart-component %1 table-head-alignment)  cart-product-ids))]]))