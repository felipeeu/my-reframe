(ns myrf.components.product
  (:require  [re-frame.core :as re-frame]
             [myrf.subs :as subs]
             [myrf.events :as events]
             [myrf.router :as router]))

(defn add-to-cart-button
  "A button to add products to cart"
  [product-id quantity]
  [:button {:on-click #(re-frame.core/dispatch [::events/add-to-cart product-id quantity])
            :class "button is-success"
            :disabled (= quantity 0)} "Add to cart"])


(defn product
  "represent a single product"
  [product-id]
  (let [name @(re-frame/subscribe [::subs/name product-id])
        price @(re-frame/subscribe [::subs/price product-id])]
    [:div {:key (str name product-id)
           :class "column"}
     [:a {:href (router/url-for :product :product-id product-id)}
      [:div {:class "box"
             :on-click #(re-frame/dispatch [::events/select-product product-id])
             :style {:cursor "pointer"
                     :background "pink"}}
       [:img {:src "no-image.png" :style {:width 80}}]
       [:p {:class "is-family-primary"} name]
       [:p {:class "is-family-code"} (str "$"  price)]]]]))

