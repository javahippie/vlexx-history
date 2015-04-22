  (ns vlexx-history.handler
      (:gen-class)
      (:use compojure.core)
      (:use cheshire.core)
      (:use ring.util.response)
      (:use overtone.at-at)


      (:require [compojure.handler :as handler]
                [ring.middleware.json :as middleware]
                [compojure.route :as route]
                [vlexx-history.fetcher :as fetcher]
                [monger.core :as mg]
                [monger.collection :as mc]))


  (defn get-all-documents[]
     (let [conn (mg/connect)
          db   (mg/get-db conn "vlexx-history")
          coll "reports"]
        (response (.toString (mc/find db coll))))
    )

   (defroutes app-routes
      (context "/documents" [] (defroutes documents-routes
        (GET  "/" [] (get-all-documents))))
      (route/not-found "Not Found"))


   (def app
      (-> (handler/api app-routes)
        (middleware/wrap-json-body)
        (middleware/wrap-json-response)))


   (def tp (mk-pool))

   (defn start-timer []
    (every 60000 fetcher/check-delays tp))
