(ns myrf.utils.helpers
  (:require [clojure.string :as string]))

(defn filter-by-name [db name]
  (->> db
       :products
       (filter #(string/includes? (:name (second %)) name))
       (into {})))


