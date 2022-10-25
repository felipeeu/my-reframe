(ns myrf.views
  (:require
   [re-frame.core :as re-frame]
   [myrf.subs :as subs]
   [myrf.components.modal :refer  [modal-generic modal-add-to-cart]]
   [myrf.components.cart :refer [cart-component]]
   [myrf.components.product :refer [product]]))

(defn header
  []
  [:nav {:class "level"}
   [:p {:class "level-item"} "login"]
   [:input {:class "level-item"}]
   [:p {:class "level-item"} "cart"]])

(defn hero
  []
  [:section {:class "hero is-primary"}
   [:div {:class "hero-body"}
    [:div {:class "container"}
     [:h1 {:class "title"} "Testing Hero"]]]])

(defn footer
  []
  [:footer {:class "footer"}
   [:div {:class "content has-text-centered"}
    [:p "fidge toys by Bulma"]]])

(defn show-modal
  []
  (let [modal-opened-status @(re-frame/subscribe [::subs/modal-opened])
        modal-opened-id @(re-frame/subscribe [::subs/modal-opened-id])
        name @(re-frame/subscribe [::subs/name modal-opened-id])]
    (modal-generic name modal-opened-id modal-opened-status modal-add-to-cart)))

(defn render-products
  []
  (let [ids  @(re-frame/subscribe [::subs/list-products-ids])]
    [:div {:class "column is-flex"}
     (doall (map  #(product %)  ids))]))

(defn render-cart
  []
  (let [ids  @(re-frame/subscribe [::subs/list-cart-products-ids])]
    [:div
     (doall (map  #(cart-component %)  ids))]))

(defn main-panel
  []
  [:div
   (header)
   [:div {:class "container"}
    (render-products)
    (render-cart)]
   (hero)
   (footer)
   (show-modal)])
