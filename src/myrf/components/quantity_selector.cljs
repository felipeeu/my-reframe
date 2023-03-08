(ns myrf.components.quantity-selector
  (:require
   [re-frame.core :as re-frame]
   [myrf.subs :as subs]
   [myrf.events :as events]))

(defn event-by-quantity-type
  "Define the event if it is a quantity in cart or not"
  [quantity-type & params]
  (let [event-key  (if (= "cart-quantity" quantity-type)
                     ::events/update-cart-quantity
                     ::events/update-quantity)]
    (re-frame.core/dispatch (apply conj [] event-key params))))

(defn change-quantity-buttons
  "Buttons to edit quantity"
  [quantity product-id quantity-type]
  [:div {:class "text-center"}
   [:button {:on-click #((event-by-quantity-type quantity-type product-id quantity inc))
             :class "mx-auto"} "+"]
   [:h6 {:class "mx-05"} quantity]
   [:button {:on-click #(if (> quantity 0)
                          (event-by-quantity-type quantity-type product-id quantity dec)
                          nil)
             :class "mx-auto"} "-"]])


(defn quantity-component
  "Change product quantity component"
  [product-id quantity]
  [:div {:class "col"}
   [change-quantity-buttons quantity product-id]
   [:button {:class "w-100"
             :on-click #(re-frame.core/dispatch [::events/reset-quantity product-id])} "Clear"]])