(ns myrf.utils.helpers
  (:require
   [clojure.string :as str]))

(defn filter-by-title
"filter function used for search products products"
  [db title]
  (->> db
       :products
       (filter #(str/includes? (:title (second %)) title))
       (into {})))


(defn normalize-db
"Normalize data when it is fetch from API"
  [db]
  (into {}
        (for [item db]
          [(:id item) (assoc item :quantity 0)])))


(defn reduce-text
"Reduce the big text for a better title"
  [text words]
  (let [part-text (take words (clojure.string/split text #" "))]
    (reduce str (for [item part-text]
                  (str item " ")))))