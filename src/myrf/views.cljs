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

(defn header
  []
  (let [cart-product-ids @(re-frame/subscribe [::subs/list-cart-products-ids])]
    [:nav
     [:ul
      [:li [:a {:href (router/url-for :home)}  "back"]]
      [:li [:a {:href (router/url-for :login)} "login"]]
      [:li [:input {:on-change (fn [e]
                                 (re-frame.core/dispatch-sync [::events/filter-by-name (-> e .-target .-value)]))}]]
      (if (empty? cart-product-ids) nil
          [:li {:class "pr-2 mt-05 float-right"}
           [:a {:href (router/url-for :cart)}  "cart"]])]]))

(defn footer-content
  []
  [:p "all-purpose store by Felipe Domingues"])

(defn split-products-into-columns
  "split lines of products into column"
  [product-ids products-columns filtered-id]
  (let [data-to-display (if (empty? filtered-id)
                          product-ids
                          filtered-id)
        mapped-products (map (fn [%] [:div {:class "w-33 mx-auto"} [product %]])
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
      [cart-table cart-ids]))

;------- product page ------ 

(defn product-panel
  []
  (let [selected-product-id @(re-frame/subscribe [::subs/selected-product])
        name @(re-frame/subscribe [::subs/name selected-product-id])
        price @(re-frame/subscribe [::subs/price selected-product-id])
        quantity @(re-frame/subscribe [::subs/quantity selected-product-id])
        inventory @(re-frame/subscribe [::subs/inventory selected-product-id])]
    [:div {:class "mt-2 w-100 bg-white"}
     [:h1 {:class "p-2 text-center"} name]
     [:div
      [:figure [:img {:class "pt-2 align-center"
                      :src "no-image.png"
                      :style {:width "10rem"
                              :height "auto"}}]]
      [:div {:class "row w-50 mx-auto pb-2"}
       [product-information quantity price inventory]
       [:div {:class "col-6"}
        [quantity-component selected-product-id quantity]
        [add-to-cart-button selected-product-id quantity]]]]]))

(defn pages
  [page-name]
  (let [product-ids  @(re-frame/subscribe [::subs/list-products-ids])
        cart-ids  @(re-frame/subscribe [::subs/list-cart-products-ids])
        filtered-id @(re-frame/subscribe [::subs/filtered-data])]
    (case page-name
      :home [render-products product-ids products-columns filtered-id]
      :cart [render-cart cart-ids]
      :product [product-panel]
      [render-products product-ids products-columns filtered-id])))

(defn main-panel
  []
  (let [active-page @(re-frame/subscribe [::subs/active-page])]
    [:div {:class "bg-secondary"}
     [:header [header]]
     [pages active-page]
     [:footer [footer-content]]]))
