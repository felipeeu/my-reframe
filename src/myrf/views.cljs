(ns myrf.views
  (:require
   [re-frame.core :as re-frame]
   [myrf.subs :as subs]
   [myrf.events :as events]))

(def styles {:header-style {:background "blue"
                            :width "100vw"
                            :height 50}
             :footer-style {:background "purple"
                            :width "100vw"
                            :height 50
                            :position "absolute"
                            :bottom 0}
             :grid-style {}})

(defn component
  [style]
  [:div {:style (style styles)}])

(defn quantity-component
  [product]
  (let [id (:id product)
        quantity (:quantity product)]
    [:div
     [:button {:on-click #(re-frame.core/dispatch [::events/increment-number id])} "+"]
     [:span quantity]
     [:button {:on-click #(re-frame.core/dispatch [::events/decrement-number id])} "-"]]))



;; (defn fetch-products
;;   []
;;   (re-frame.core/dispatch [::events/fetch-products]))

(defn current-product
  [product]
  [:div {:key (str (:name product) (:id product))} [:p  (:name product)] (quantity-component product)])

(defn render-products
  []
  (let [products (re-frame/subscribe [::subs/products])]

    [:div {:style (:grid-style styles)} (map  #(current-product %)  @products)]))

(defn main-panel
  []
  [:div
   (component :header-style)
   (render-products)
   (component :footer-style)])
