(ns testapp.core
  (:require
   [bidi.bidi :as b]
   [re-frame.core :refer [dispatch subscribe reg-event-db reg-sub]]
   [reagent.core :as reagent :refer [atom]]
   [accountant.core :as accountant]))

(def routes
  ["/" {"" :home
        "about" :about}])

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to reagent"]
   [:div [:a {:href (b/path-for routes :about)} "go to about page"]]])

(defn about-page []
  [:div [:h2 "About reagent"]
   [:div [:a {:href (b/path-for routes :home)} "go to the home page"]]])


(reg-event-db
 ::nav-handler
 (fn [db [_ handler]]
   (assoc db :view
          (case (:handler handler)
            :home (reagent/render [home-page] (.getElementById js/document "app"))
            :about (reagent/render [about-page] (.getElementById js/document "app"))))))

(defn init! []
  (accountant/configure-navigation!
   {:nav-handler
    (fn [path]
      (dispatch
       [::nav-handler
        (b/match-route routes path)]))
    :path-exists?
    (fn [path]
      (some? (b/match-route routes path)))})
  (accountant/dispatch-current!))
