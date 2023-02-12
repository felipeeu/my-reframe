(ns myrf.views
  (:require
   [re-frame.core :as re-frame]
   [myrf.subs :as subs]
   [myrf.router :as router]
   [myrf.components.cart :refer [cart-component]]
   [myrf.components.product :refer [product add-to-cart-button]]
   [myrf.components.quantity-selector :refer [quantity-component]]
   [myrf.utils.price :refer [format-price]]))


(defn header
  []
  [:nav
   [:ul
    [:li [:a {:href (router/url-for :home)}  "back"]]
    [:li [:a {:href (router/url-for :login)} "login"]]
    [:li [:a {:href (router/url-for :cart)} "cart"]]
    [:li [:input]]]])



(defn footer-content
  []
  [:p "all-purpose store by Felipe Domingues"])


(defn render-products
  []
  (let [ids  @(re-frame/subscribe [::subs/list-products-ids])]
    [:section {:class "row pt-2"}
     (doall (map  #(product %)  ids))]))

(defn render-cart
  []
  (let [ids  @(re-frame/subscribe [::subs/list-cart-products-ids])]
    [:div
     [:table
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
    [:div
     [:h1 name]
     [:h2  "testing subtitle testing subtitle testing subtitle"]
     [:div
      [:div  [:figure
              [:img {:src "no-image.png"}]]]
      [:section {:class "row"}
       [:div
        [:p [:h6 "Price:"]
         [:span  (format-price  price)]]
        [:p  [:h6 "Quantity:"]
         [:span   quantity]]
        [:p   [:h6 "Inventory:"]
         [:span   inventory]]]
       [:div
        [quantity-component selected-product-id quantity]
        [add-to-cart-button selected-product-id quantity]]]]]))

(defn pages
  [page-name]
  [:section
   (case page-name
     :home [render-products]
     :cart [render-cart]
     :product [product-panel]
     [render-products])])

(defn main-panel
  []
  (let [active-page @(re-frame/subscribe [::subs/active-page])]
    [:div {:class "bg-secondary"}
     [:header [header]]
     [:section [pages active-page]]
     [:footer [footer-content]]]))
