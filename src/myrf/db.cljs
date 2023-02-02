(ns myrf.db)

(def default-db
  {:products  {"1" {:name "spinner"
                    :quantity 0
                    :inventory 10
                    :image "no-image.png"
                    :id "1"
                    :price 20
                    :cart-added false}
               "2" {:name "mush mush"
                    :quantity 0
                    :inventory 15
                    :image "no-image.png"
                    :id "2"
                    :price 14.8
                    :cart-added false}
               "3"  {:name "sensorial tube"
                     :quantity 0
                     :inventory 6
                     :image "no-image.png"
                     :id "3"
                     :price 10.2
                     :cart-added false}
               "4" {:name "peanut"
                    :quantity 0
                    :inventory 8
                    :image "no-image.png"
                    :id "4"
                    :price 15.22
                    :cart-added false}}
   :cart {"4" {:quantity 2}}
   :modal-opened {}})