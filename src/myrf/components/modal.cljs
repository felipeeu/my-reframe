(ns myrf.components.modal)

(defn modal
  [title component is-active]
  [:div {:class (str "modal" is-active)}
   [:div {:class "modal-background"}
    [:div {:class "modal-card"}
     [:header {:class "modal-card-head"}
      [:p {:class "modal-card-title"} title]
      [:button {:class "modal-close is-small" :aria-label "close"}]]
     [:section {:class "modal-card-body"}
      (component)]
     [:footer {:class "modal-card-foot"}]]]])