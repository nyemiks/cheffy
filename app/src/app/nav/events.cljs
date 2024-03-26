(ns app.nav.events
  (:require
   [re-frame.core :refer [reg-event-db]]
   [app.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   ))

(reg-event-db
 ::initialize-db
 (fn-traced [_ _]
  ; db/default-db
            db/initial-app-db
            )
 )


(reg-event-db 
 ::set-active-nav
 (fn [db [_ active-nav]]
   (.info js/console "active-nav: " active-nav)
      (assoc-in db [:nav :active-nav] active-nav)
   )
 )
