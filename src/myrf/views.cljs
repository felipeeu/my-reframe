(ns myrf.views
  (:require
   [re-frame.core :as re-frame]
   [myrf.subs :as subs]
   [myrf.router :as router]
   [myrf.components.cart :refer [cart-component]]
   [myrf.components.product :refer [product add-to-cart-button]]
   [myrf.components.quantity-selector :refer [quantity-component]]))

(defn header
  []
  [:nav {:class "level"}
   [:a {:href (router/url-for :home)} [:p "back"]]
   [:a {:href (router/url-for :login)
        :class "level-item"} "login"]
   [:input {:class "level-item"}]
   [:a {:href (router/url-for :cart)
        :class "level-item"} "cart"]])

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


(defn render-products
  []
  (let [ids  @(re-frame/subscribe [::subs/list-products-ids])]
    [:div {:class "column is-flex"}
     (doall (map  #(product %)  ids))]))

(defn render-cart
  []
  (let [ids  @(re-frame/subscribe [::subs/list-cart-products-ids])]
    [:div {:class "is-flex is-justify-content-center"}
     [:table {:class "table is-fullwidth is-striped"}
      [:thead [:tr
               [:th "Product"]
               [:th "Name"]
               [:th "Price"]
               [:th "Quantity"]
               [:th "Total"]]]
      [:tbody
       (doall (map  #(cart-component %)  ids))]]]))

;------- product page ------ 

(defn product-panel
  []
  (let [selected-product-id @(re-frame/subscribe [::subs/selected-product])
        name @(re-frame/subscribe [::subs/name selected-product-id])
        price @(re-frame/subscribe [::subs/price selected-product-id])
        quantity @(re-frame/subscribe [::subs/quantity selected-product-id])
        inventory @(re-frame/subscribe [::subs/inventory selected-product-id])]
    [:section {:class "section is-fluid"}
     [:h1 {:class "title"} name]
     [:h2 {:class "subtitle"} "testing subtitle testing subtitle testing subtitle"]
     [:div {:class "container is-flex"}
      [:div {:class "section"} [:figure {:class "image is-96x96"}
                                [:img {:src "no-image.png"}]]]
      [:div {:class "section is-flex is-flex-direction-column is-justify-content-space-evenly"}
       [:p {:class "has-text-weight-bold"}  "Price: "
        [:span {:class "has-text-weight-normal"} (str "$" price)]]
       [:p {:class "has-text-weight-bold"}  "Quantity: "
        [:span {:class "has-text-weight-normal"}  quantity]]
       [:p {:class "has-text-weight-bold"}  "Inventory: "
        [:span {:class "has-text-weight-normal"}  inventory]]]
      [:div {:class "section is-flex is-flex-direction-column is-justify-content-space-evenly"}
       [quantity-component selected-product-id quantity]
       [add-to-cart-button selected-product-id quantity]]]]))

(defn pages
  [page-name]
  (case page-name
    :home [render-products]
    :cart [render-cart]
    :product [product-panel]
    [render-products]))

(defn main-panel
  []
  (let [active-page @(re-frame/subscribe [::subs/active-page])]
    [:div
     (header)
     [:div {:class "container"}
      (pages active-page)]
     (hero)
     (footer)]))
