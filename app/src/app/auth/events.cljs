(ns app.auth.events
  (:require [app.nav.events :as nav-evts]
            [re-frame.core :refer [reg-event-db reg-event-fx]])
  
  )



(reg-event-fx
 ::sign-up
 (fn [{:keys [db]} [_ {:keys [first-name last-name email password]}]]  
    {:db (-> db 
        (assoc-in [:auth :uid] email)
        (assoc-in [:users email] {:uid email
                                  :profile {
                                            :first-name first-name
                                            :last-name last-name
                                            :email email
                                            :password password
                                            :img  "img/avatar.jpg"
                                            }
                                  :saved #{}
                                  :inboxes {}
                                  })
        )
      :dispatch [::nav-evts/set-active-nav :saved]
     }
   )
 )


(reg-event-fx
 ::log-in
 (fn [{:keys [db]} [_ {:keys [email password]}]]
   ;(.info js/console "email: " email  " password: " password)
    (let [
          user (get-in db [:users email])
          correct-password? (= (get-in user [:profile :password]) password)
          ]
       (.info js/console "user: " user  " correct password ? " correct-password?)
       (cond 
         (not user) {:db (assoc-in db [:errors :email] "user not found")}
         (not correct-password?)  {:db (assoc-in db [:errors :email] "Wrong password")}
         correct-password?  {:db (-> db
                                (assoc-in [:auth :uid] email)
                                (update-in [:errors] dissoc :email)
                                )
                             :dispatch [::nav-evts/set-active-nav :saved]
                             }
         
         )
      )
   )
 )


(reg-event-fx 
 ::log-out
 (fn [{:keys [db] } _]
   {
    :db (assoc-in db [:auth :uid] nil)
    :dispatch [::nav-evts/set-active-nav :recipes]
    }
   )
 )


(reg-event-db 
 ::update-profile
 (fn [db [_ profile]]
    (let [uid (get-in db [:auth :uid])]
          (update-in db [:users uid :profile] merge (select-keys profile [:first-name :last-name])) 
           )
      )
   )
 


(reg-event-fx 
 ::delete-account
 (fn [{:keys [db] } _]
   (let [uid (get-in db [:auth :uid])] 
     {:db (-> db
              (assoc-in [:auth :uid] nil)
              (update-in [:users] dissoc uid)
              )
      :dispatch [::nav-evts/set-active-nav :recipes]
      }
     )
   
   )
 )
