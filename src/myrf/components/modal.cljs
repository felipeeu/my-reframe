(ns myrf.components.modal
  (:require [re-frame.core :as re-frame]
            [myrf.subs :as subs]
            [myrf.events :as events]
            [myrf.components.quantity-selector :refer [quantity-component]]))


(defn modal-add-to-cart
  [title modal-opened-id]
  (let [quantity  @(re-frame/subscribe [::subs/quantity modal-opened-id])]
    [:div {:class "modal-card"}
     [:header {:class "modal-card-head"}
      [:p {:class "modal-card-title"} title]
      [:button {:on-click #(re-frame.core/dispatch [::events/close-modal]) :class "modal-close is-large" :aria-label "close"}]]
     [:section {:class "modal-card-body"}
      (quantity-component modal-opened-id quantity)]
     [:footer {:class "modal-card-foot"}
      [:button {:on-click #(re-frame.core/dispatch [::events/add-to-cart modal-opened-id quantity])} "add to cart"]]]))

(defn modal-generic
  [title modal-opened-id is-active modal-type]
  [:div {:class (str "modal" is-active)}
   [:div {:class "modal-background"}
    (modal-type title modal-opened-id)]])