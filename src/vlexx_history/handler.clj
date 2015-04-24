  (ns vlexx-history.handler
      (:gen-class)
      (:use compojure.core)
      (:use cheshire.core)
      (:use ring.util.response)

      (:require [compojure.handler :as handler]
                [ring.middleware.json :as middleware]
                [compojure.route :as route]
                [vlexx-history.fetcher :as fetcher]
                [vlexx-history.database :as database]
                [vlexx-history.task :as task]
                [clojure.tools.logging :as log]))

   (defroutes app-routes
      (context "/trains/all" [] (defroutes documents-routes
        (GET  "/" [] (response (database/get-all-documents)))))

      (context "/trains/top10/:date" [date] (defroutes documents-routes
        (GET  "/" [] (response (database/get-delayed-top10 date)))))

      (route/not-found {:status 404}))

   (def app
      (-> (handler/api app-routes)
        (middleware/wrap-json-body)
        (middleware/wrap-json-response)))

   (defn start-timer []
     "Schedules the request to the vlexx server"
     (log/info "Init method was called")
     (task/schedule fetcher/check-delays))
