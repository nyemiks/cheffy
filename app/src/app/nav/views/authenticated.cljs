(ns app.nav.views.authenticated
  (:require 
   ["@smooth-ui/core-sc" :refer [Box]]
   [app.nav.views.nav-item :refer [nav-item]]
    [app.nav.events :as events]  
    [app.nav.subs :as subs]
   [re-frame.core :as rf])
  )

(defn authenticated []
  (let [active-nav   @(rf/subscribe [::subs/active-nav])
        nav-items [{:id :saved
                    :name "Saved"
                    :href "#saved"
                    :dispatch #(rf/dispatch [::events/set-active-nav :saved])
                   }
                   {:id :recipes
                    :name "Recipes"
                    :href "#recipes"
                    :dispatch #(rf/dispatch [::events/set-active-nav :recipes])
                   }
                   {:id :inboxes
                    :name "Inbox"
                    :href "#inbox"
                    :dispatch #(rf/dispatch [::events/set-active-nav :inboxes])
                   }
                   {:id :become-a-chef
                    :name "Chef"
                    :href "#become-a-chef"
                    :dispatch #(rf/dispatch [::events/set-active-nav :become-a-chef])
                   }
                   {:id :profile
                    :name "Profile"
                    :href "#profile"
                    :dispatch #(rf/dispatch [::events/set-active-nav :profile])
                   }
                  ]
        ] 
    
      [:> Box {
           :display "flex"
           :justify-content "flex-end"
           :py 1
           }
          (for [{:keys [id name href dispatch]} nav-items]
             ^{:key id} [nav-item {
                                            :id id
                                            :name name
                                            :href href
                                            :dispatch dispatch
                                            :active-nav active-nav
                                            }
                                  ]
            )
       ]

    )
 
  
  )
