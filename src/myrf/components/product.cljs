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
    [:div {:key (str name product-id) :class "column"}
     [:div {:class "box"}
      [:img {:src "no-image.png" :style {:width 80}}]
      [:p {:class "is-family-primary"} name]
      [:p {:class "is-family-code"} (str "$"  price)]
      [:button {:on-click #(re-frame.core/dispatch [::events/show-modal product-id quantity]) :class "button is-info is-light "} "buy"]]]))
