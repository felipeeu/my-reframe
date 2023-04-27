(ns myrf.components.theme
  (:require [clojure.string :as str]
            [myrf.events :as events]
            [myrf.subs :as subs]
            [re-frame.core :refer [dispatch subscribe]]))

(def theme-list '("Dark" "Skeleton" "Sepia" "Milligram" "Pure" "Sakura" "Bootstrap" "Medium" "Tufte"))

(defn theme-options
  [listed-theme]
  (let [value (clojure.string/lower-case listed-theme)]
    [:option {:key listed-theme
              :value value} listed-theme]))

(defn theme-selector
  []
  (let [selected-theme @(subscribe [::subs/theme])]

    [:select {:value selected-theme :on-change (fn [evt]
                                                 (dispatch [::events/change-theme (-> evt .-target .-value)]))}
     (map  #(theme-options %) theme-list)]))

