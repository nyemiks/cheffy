(ns app.core
  (:require
   [app.config :as config]
   [reagent.dom :as rdom]
   [re-frame.core :as rf]
    [app.db]
   
   [app.recipes.views.recipes :refer [recipes]]
   [app.inbox.views.inboxes :refer [inboxes]]
   [app.auth.views.profile :refer [profile]]
   [app.auth.views.sign-up :refer [sign-up]]
   [app.auth.views.log-in :refer [log-in]]
   [app.become-a-chef.views.become-a-chef :refer [become-a-chef]]
   ;; -- nav ---
   [app.nav.views.nav :refer [nav]]
   [app.nav.events :as events] 
   [app.auth.events]
   [app.auth.subs]
   ;[app.nav.subs]
   ;[app.nav.events] 
   [app.theme :refer [cheffy-theme]] 
   ["@smooth-ui/core-sc" :refer [Normalize ThemeProvider Grid Row Col]] 
   [app.nav.subs :as subs]
   
   ))


(defn pages [page-name]
  (case page-name
    :sign-up [sign-up]
    :log-in  [log-in]
    :profile [profile]
    :become-a-chef [become-a-chef]
    :inboxes [inboxes]
    :recipes [recipes]
    [recipes]
    )
  )


(defn app
  []
(let [
      active-nav @(rf/subscribe [::subs/active-nav])
      ]
  
  [:<> 
    ; [(r/adapt-react-class Normalize)]
     [:> Normalize]
    ; [(r/adapt-react-class Button)  "click me !"]
    [:> ThemeProvider {:theme cheffy-theme}
     [:> Grid {:fluid false} 
       [:> Row 
         [:> Col 
          [nav]
          [pages active-nav]
          ]
          
        ]
      
      ] 
    
     ]
   
      
   ]
  )
  
  )

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [app] root-el)))

(defn init []
  (rf/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
