(ns myrf.components.theme
  (:require [clojure.string :as str]
            [myrf.events :as events]
            [myrf.subs :as subs]
            [re-frame.core :refer [dispatch-sync subscribe]]))

(def theme-list '("Skeleton"  "Dark" "Sepia" "Milligram" "Pure" "Sakura" "Bootstrap" "Medium" "Tufte"))

(defn theme-selector
  []
  [:select {:type "select-one" :on-change (fn [evt]
                                            (dispatch-sync [::events/change-theme (-> evt .-target .-value)]))}
   (map (fn [%] [:option {:key %
                          :value (clojure.string/lower-case %)} %]) theme-list)])

