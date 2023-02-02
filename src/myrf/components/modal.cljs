(ns myrf.components.modal)


(defn modal-generic
  [title modal-opened-id is-active modal-type]
  [:div {:class (str "modal" is-active)}
   [:div {:class "modal-background"}
    (modal-type title modal-opened-id)]])