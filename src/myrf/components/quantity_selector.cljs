(ns myrf.components.quantity-selector
  (:require
   [re-frame.core :as re-frame]
   [myrf.subs :as subs]
   [myrf.events :as events]))

(defn event-by-quantity-type
  "define the event if it is a quantity in cart or not"
  [quantity-type & params]
  (let [event-key  (if (= "cart-quantity" quantity-type)
                     ::events/update-cart-quantity
                     ::events/update-quantity)]
    (re-frame.core/dispatch [event-key (first params) (second params) (last params)])))

(defn change-quantity-buttons
  "buttons to edit quantity"
  [quantity product-id quantity-type]
  (let [inventory @(re-frame/subscribe [::subs/inventory product-id])]
    [:div {:class "container is-flex is-justify-content-center"}
     [:button {:on-click #(if  (< quantity inventory)
                            (event-by-quantity-type quantity-type product-id quantity inc)
                            nil)
               :class "button is-primary p-2"} "+"]
     [:span {:class "p-3"} quantity]
     [:button {:on-click #(if (> quantity 0)
                            (event-by-quantity-type quantity-type product-id quantity dec)
                            nil)
               :class "button is-primary  p-2"} "-"]]))


(defn quantity-component
  [product-id quantity]
  [:div {:class "container is-flex is-flex-direction-column"}
   [:div {:class "container is-flex is-justify-content-center"}
    [change-quantity-buttons quantity product-id]]
   [:div {:class "container is-flex is-justify-content-center pt-3"}
    [:button {:on-click #(re-frame.core/dispatch [::events/reset-quantity product-id])
              :class "button is-warning "} "Clear"]]])