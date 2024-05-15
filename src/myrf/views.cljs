(ns myrf.views
  (:require [myrf.components.cart :refer [cart-table]]
            [myrf.components.product :refer [add-to-cart-button product
                                             product-information]]
            [myrf.components.quantity-selector :refer [quantity-component]]
            [myrf.components.header :refer [header-page]]
            [myrf.subs :as subs]
            [myrf.utils.constants :refer [products-columns]]
            [re-frame.core :refer [subscribe]]))

(defn loader
  []
  [:div {:class "mt-2 pt-2 mx-auto w-25 text-center"} "Loading ..."])


(defn footer-content
  []
  [:p "all-purpose store by Felipe Domingues"])

(defn split-products-into-columns
  "split lines of products into column"
  [product-ids products-columns filtered-id]
  (let [data-to-display (if (empty? filtered-id)
                          product-ids
                          filtered-id)
        mapped-products (map (fn [%] [:div {:class "w-33 mx-auto"
                                            :key (random-uuid)} [product %]])
                             data-to-display)
        count-ids (count data-to-display)
        products-panel (if (< count-ids products-columns)
                         mapped-products
                         (partition-all products-columns mapped-products))]

    products-panel))

(defn render-products
  "Render products and split lines into colums"
  [products-columns]
  (let [product-ids  @(subscribe [::subs/list-products-ids])
        filtered-id @(subscribe [::subs/filtered-data])
        loading @(subscribe [::subs/loading])]
    (if loading (loader) [:div {:class "pt-2"}
                          (->> (split-products-into-columns product-ids products-columns filtered-id)
                               (map (fn [%] [:div {:key (random-uuid)
                                                   :class "row pt-2 text-center"} %]))
                               (doall))])))

(defn render-cart
  "Render cart table with products"
  []
  (let [cart-ids  @(subscribe [::subs/list-cart-products-ids])]
    (if (empty? cart-ids) nil
        [:figure {:class "mt-2"}  [cart-table cart-ids]])))

;------- product page ------ 
(defn product-details
  "Product page routed when a product is clicked"
  []
  (let [selected-product-id @(subscribe [::subs/param-id])
        title @(subscribe [::subs/title selected-product-id])
        price @(subscribe [::subs/price selected-product-id])
        quantity @(subscribe [::subs/quantity selected-product-id])
        image @(subscribe [::subs/image selected-product-id])
        rate @(subscribe [::subs/rate selected-product-id])
        description @(subscribe [::subs/description selected-product-id])]
    [:div {:class "mx-auto mt-2 w-75 bg-white"}
     [:h1 {:class "p-2 text-center"} title]
     [:div
      [:figure
       [:div {:class ""}
        [:img {:class "float-left mx-2  w-25"
               :src image}]]
       [:div {:class "row mx-auto pt-2 pb-2"}
        [product-information rate price description]
        [:div
         [quantity-component selected-product-id quantity]
         [add-to-cart-button selected-product-id quantity]]]]]]))

(defn login
  "Login page - routed when login is clicked"
  []
  [:section {:class "w-25 mx-auto"}
   [:h1 "Login"]
   [:div   [:span "user:"] [:input]]
   [:div  [:span "password:"] [:input]]
   [:button {:class "m-0 mt-1 w-100"} "Login"]])

(defn register
  "Register page - routed when register is clicked"
  []
  [:section {:class "w-25 mx-auto"}
   [:h1 "Register"]
   [:div   [:span "name:"] [:input]]
   [:div  [:span "e-mail:"] [:input]]
   [:div   [:span "password:"] [:input]]
   [:div  [:span "repeat password:"] [:input]]
   [:button {:class "m-0 mt-1 w-100"} "Register"]])



(defn pages
  "Switch to pages"
  [page-name]
  (case page-name
    :home [render-products products-columns]
    :cart [render-cart]
    :product [product-details]
    :login  [login]
    :register [register]
    [render-products  products-columns]))



(defn main-panel
  "Main function"
  []
  (let [active-page @(subscribe [::subs/active-page])
        cart-product-ids @(subscribe [::subs/list-cart-products-ids])]
    [:div {:class "bg-secondary"}
     [:header [header-page cart-product-ids]]
     [pages active-page]
     [:footer [footer-content]]]))
