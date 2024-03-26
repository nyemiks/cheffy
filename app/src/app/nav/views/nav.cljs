(ns app.nav.views.nav
  (:require [app.nav.views.authenticated :refer [authenticated]]
            [app.nav.views.public :refer [public]]
            [re-frame.core :as rf]
            [app.auth.subs :as subs]
            )
  )


(defn nav 
  []
  (let [logged-in? @(rf/subscribe [::subs/logged-in?])]
    (if logged-in? 
      [authenticated] 
      [public])
    )
  )