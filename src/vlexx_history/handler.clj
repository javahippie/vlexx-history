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

   (defn remove-id [response]
     (encode (map #(dissoc % :_id) response)))

   (defn get-all-trains []
     (response (remove-id (database/get-all-documents))))

   (defn get-delayed-top10 [date]
     (response (remove-id (database/get-delayed-top10 date))))

   (defroutes app-routes
      (context "/trains/all" [] (defroutes documents-routes
        (GET  "/" [] (get-all-trains))))

      (context "/trains/top10/:date" [date] (defroutes documents-routes
        (GET  "/" [] (get-delayed-top10 date))))

      (route/resources "/")

      (route/not-found "Page not found"))

   (def app
      (-> (handler/api app-routes)
        (middleware/wrap-json-response)))

   (defn start-timer []
     "Schedules the request to the vlexx server"
     (log/info "Init method was called")
     (task/schedule fetcher/check-delays))
