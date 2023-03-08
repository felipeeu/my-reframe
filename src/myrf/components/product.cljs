(ns myrf.components.product
  (:require  [re-frame.core :as re-frame]
             [myrf.subs :as subs]
             [myrf.events :as events]
             [myrf.router :as router]
             [myrf.utils.price :refer [format-price]]
             [myrf.utils.helpers :refer [reduce-text]]))

(defn add-to-cart-button
  "A button to add products to cart"
  [product-id quantity]
  [:button {:class "w-100"
            :on-click #(re-frame.core/dispatch [::events/add-to-cart product-id quantity])
            :disabled (= quantity 0)} "Add to cart"])

(defn product
  "Represent a single product"
  [product-id]
  (let [title @(re-frame/subscribe [::subs/title product-id])
        price @(re-frame/subscribe [::subs/price product-id])
        image @(re-frame/subscribe [::subs/image product-id])]
    [:div {:key (str title product-id)
           :class "card m-2 col bg-white w-75"}
     [:a {:href (router/url-for :product :product-id product-id)}
      [:div {:on-click #(re-frame/dispatch [::events/select-product product-id])}
       [:img {:class "pt-2" :src image :style {:height "8rem"}}]
       [:h4 {:style {:height "4rem"}} (reduce-text title 4)]
       [:h5 {:class "pb-2"} (format-price  price)]]]]))

(defn product-information
  "Information that should appear when product is selected"
  [rate price description]
  [:div {:class "col-6"}
   [:div [:h6 {:class "pr-05"} "Price:"]
    [:span  (format-price  price)]]
   [:div  [:h6 {:class "pr-05"} "Rate:"]
    [:span   rate]]
   [:p description]])