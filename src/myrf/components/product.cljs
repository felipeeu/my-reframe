(ns myrf.components.product
  (:require  [re-frame.core :as re-frame]
             [myrf.subs :as subs]
             [myrf.events :as events]
             [myrf.router :as router]
             [myrf.utils.price :refer [format-price]]))

(defn add-to-cart-button
  "A button to add products to cart"
  [product-id quantity]
  [:button {:class "w-100"
            :on-click #(re-frame.core/dispatch [::events/add-to-cart product-id quantity])
            :disabled (= quantity 0)} "Add to cart"])


(defn product
  "Represent a single product"
  [product-id]
  (let [name @(re-frame/subscribe [::subs/name product-id])
        price @(re-frame/subscribe [::subs/price product-id])]
    [:div {:key (str name product-id)
           :class "card m-2 col"}
     [:a {:href (router/url-for :product :product-id product-id)}
      [:div {:on-click #(re-frame/dispatch [::events/select-product product-id])}
       [:img {:src "no-image.png" :style {:width 80}}]
       [:h4  name]
       [:p (format-price  price)]]]]))

(defn product-information
  "Information that should appear when product is selected"
  [quantity price inventory]
  [:div {:class "col-6"}
   [:p [:h6 {:class "pr-05"} "Price:"]
    [:span  (format-price  price)]]
   [:p  [:h6 {:class "pr-05"} "Quantity:"]
    [:span   quantity]]
   [:p   [:h6 {:class "pr-05"} "Inventory:"]
    [:span   inventory]]])