(ns myrf.components.header
  (:require
   [myrf.components.theme :refer [theme-selector]]
   [myrf.events :as events]
   [myrf.router :as router]
   [re-frame.core :refer [dispatch-sync]]))

(defn link
  ([name]
   [:li
    [:a {:href (router/url-for (keyword name))}  name]])
  ([name condition] (if (true? condition) (link name) nil)))

(defn header-page
  [cart-product-ids]
  (let [empty-cart (empty? cart-product-ids)
        logged false]
    [:nav
     [:ul
      [link "home"]
      [:li  [:input {:on-change (fn [e]
                                  (dispatch-sync [::events/filter-by-title (-> e .-target .-value)]))}]]
      [:li  [theme-selector]]
      [link "cart" (not empty-cart)]
      [link "login" logged]
      [link "register"]]]))