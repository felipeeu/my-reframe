(ns myrf.views
  (:require
   [myrf.components.cart :refer [cart-table]]
   [myrf.components.product :refer [add-to-cart-button product
                                    product-information]]
   [myrf.components.quantity-selector :refer [quantity-component]]
   [myrf.events :as events]
   [myrf.router :as router]
   [myrf.subs :as subs]
   [myrf.utils.constants :refer [products-columns]]
   [re-frame.core :as re-frame]))



(defn header-page
  [cart-product-ids]
  [:nav
   [:ul
    [:li [:a {:href (router/url-for :home)}  "back"]]
    [:li [:input {:on-change (fn [e]
                               (re-frame.core/dispatch-sync [::events/filter-by-name (-> e .-target .-value)]))}]]
    (if (empty? cart-product-ids) nil
        [:li {:class "pr-2 mt-05 float-right"}
         [:a {:href (router/url-for :cart)}  "cart"]])
    (if false
      [:li {:class "pr-2 mt-05 float-right"}
       [:a {:href (router/url-for :login)}  "login"]]
      [:li {:class "pr-2 mt-05 float-right"}
       [:a {:href (router/url-for :register)}  "register"]])]])

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
  [product-ids products-columns filtered-id]
  [:div
   (->> (split-products-into-columns product-ids products-columns filtered-id)
        (map (fn [%] [:div {:key (random-uuid)
                            :class "row pt-2 text-center"} %]))
        (doall))])

(defn render-cart
  [cart-ids]
  (if (empty? cart-ids) nil
      [:figure {:class "mt-2"}  [cart-table cart-ids]]))

;------- product page ------ 

(defn product-details
  [selected-product-id]
  (let [name @(re-frame/subscribe [::subs/name selected-product-id])
        price @(re-frame/subscribe [::subs/price selected-product-id])
        quantity @(re-frame/subscribe [::subs/quantity selected-product-id])
        inventory @(re-frame/subscribe [::subs/inventory selected-product-id])]
    [:div {:class "card mx-auto mt-2 w-75 bg-white"}
     [:h1 {:class "p-2 text-center"} name]
     [:div
      [:figure [:img {:class "pt-2 align-center"
                      :src "no-image.png"
                      :style {:width "10rem"
                              :height "auto"}}]]
      [:div {:class "row w-50 mx-auto pt-2 pb-2"}
       [product-information quantity price inventory]
       [:div {:class "col-6"}
        [quantity-component selected-product-id quantity]
        [add-to-cart-button selected-product-id quantity]]]]]))

(defn login
  []
  [:section {:class "w-25 mx-auto"}
   [:h1 "Login"]
   [:div   [:span "user:"] [:input]]
   [:div  [:span "password:"] [:input]]
   [:button {:class "m-0 mt-1 w-100"} "Login"]])

(defn register
  []
  [:section {:class "w-25 mx-auto"}
   [:h1 "Register"]
   [:div   [:span "name:"] [:input]]
   [:div  [:span "e-mail:"] [:input]]
   [:div   [:span "password:"] [:input]]
   [:div  [:span "repeat password:"] [:input]]
   [:button {:class "m-0 mt-1 w-100"} "Register"]])

(defn pages
  [page-name]
  (let [product-ids  @(re-frame/subscribe [::subs/list-products-ids])
        cart-ids  @(re-frame/subscribe [::subs/list-cart-products-ids])
        filtered-id @(re-frame/subscribe [::subs/filtered-data])
        selected-product-id @(re-frame/subscribe [::subs/selected-product])]
    (case page-name
      :home [render-products product-ids products-columns filtered-id]
      :cart [render-cart cart-ids]
      :product [product-details selected-product-id]
      :login  [login]
      :register [register]
      [render-products product-ids products-columns filtered-id])))

(defn main-panel
  []
  (let [active-page @(re-frame/subscribe [::subs/active-page])
        cart-product-ids @(re-frame/subscribe [::subs/list-cart-products-ids])]
    [:div {:class "bg-secondary"}
     [:header [header-page cart-product-ids]]
     [pages active-page]
     [:footer [footer-content]]]))
