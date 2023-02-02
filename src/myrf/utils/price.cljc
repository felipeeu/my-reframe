(ns myrf.utils.price
  (:require [goog.string :as gstring]))

(defn format-price
  [number]
  (str "$" (gstring/format "%.2f" number)))