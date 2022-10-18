(ns myrf.views
  (:require
   [re-frame.core :as re-frame]
   [myrf.subs :as subs]
   [myrf.events :as events]))

(def styles {:body-style {:width "100vw"
                          :height "100vh"
                          :background "pink"}

             :header-style {:background "blue"
                            :width "100vw"
                            :height 50}
             :footer-style {:background "purple"
                            :width "100vw"
                            :height 50}
             :grid-style {:background "green"
                          :display "grid"
                          :grid-template-columns "1fr 1fr 1fr"}
             :image-style {:border-style "solid"
                           :border-width "thin"
                           :width 100
                           :margin 2}})

(defn component
  [style]
  [:div {:style (style styles)}])

(defn quantity-component
  [product-id]
  (let [quantity  @(re-frame/subscribe [::subs/quantity product-id])
        inventory @(re-frame/subscribe [::subs/inventory product-id])]
    [:div
     [:button {:on-click #(if  (< quantity inventory) (re-frame.core/dispatch [::events/update-quantity product-id  quantity inc]) nil)} "+"]
     [:span quantity]
     [:button {:on-click #(if (> quantity 0) (re-frame.core/dispatch [::events/update-quantity product-id quantity dec]) nil)} "-"]
     [:button {:on-click #(re-frame.core/dispatch [::events/reset-quantity product-id])} "empty"]]))

(defn product
  "represent a single product"
  [product-id]
  (let [image-style (:image-style styles)
        name (re-frame/subscribe [::subs/name product-id])
        price (re-frame/subscribe [::subs/price product-id])]
    [:div {:key (str name product-id)}
     [:img {:src "no-image.png" :style image-style}]
     [:p  @name]
     [:p (str "$"  @price)]
     [:button "buy"]
     (quantity-component product-id)]))

(defn render-products
  []
  (let [ids  (re-frame/subscribe [::subs/list-products-ids])]
    [:div {:style (:grid-style styles)}
     (doall (map  #(product %)  @ids))]))

(defn main-panel
  []
  [:div
   (component :header-style)
   [:div {:style (:body-style styles)}
    (render-products)]
   (component :footer-style)])
