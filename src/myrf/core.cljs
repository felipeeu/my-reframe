(ns myrf.core
  (:require
   [myrf.config :as config]
   [myrf.events :as events]
   [myrf.views :as views]
   [myrf.router :as router]
   [re-frame.core :as re-frame]
   [reagent.dom :as rdom]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (router/start!)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
