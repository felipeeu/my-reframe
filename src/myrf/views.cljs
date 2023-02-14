(ns myrf.views
  (:require
   [myrf.components.cart :refer [cart-component cart-table]]
   [myrf.components.product :refer [add-to-cart-button product product-information]]
   [myrf.components.quantity-selector :refer [quantity-component]]
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
      [:li [:input]]
      (if (empty? cart-product-ids) nil
          [:li {:class "pr-2 mt-05 float-right"}
           [:a {:href (router/url-for :cart)}  "cart"]])]]))

(defn footer-content
  []
  [:p "all-purpose store by Felipe Domingues"])

(defn render-products
  "Render products and pass number of columns as parameters"
  [columns]
  (let [ids  @(re-frame/subscribe [::subs/list-products-ids])]
    [:div
     (->> ids
          (map  #(product %))
          (partition columns)
          (map (fn [%] [:div {:key (rand-int (count ids))
                              :class "row pt-2 text-center"} %]))
          (doall))]))


(defn render-cart
  []
  (let [ids  @(re-frame/subscribe [::subs/list-cart-products-ids])]
    (if (empty? ids) nil
        [cart-table ids])))

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
  (case page-name
    :home [render-products products-columns]
    :cart [render-cart]
    :product [product-panel]
    [render-products products-columns]))

(defn main-panel
  []
  (let [active-page @(re-frame/subscribe [::subs/active-page])]
    [:div {:class "bg-secondary"}
     [:header [header]]
     [pages active-page]
     [:footer [footer-content]]]))
