(ns myrf.router
  (:require [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [myrf.events :as events]
            [re-frame.core :as re-frame]))

(def routes
  ["/" {"" :home
        "login"    :login
        "register" :register
        "cart"     :cart
        "product/" {[:product-id] :product}}])

(def history
  (let [dispatch #(re-frame.core/dispatch [::events/set-active-page {:page (:handler %)
                                                                     :product      (get-in % [:route-params :product-id])}])
        match #(bidi/match-route routes %)]
    (pushy/pushy dispatch match)))


(defn start!
  []
  (pushy/start! history))

(def url-for (partial bidi/path-for routes))

