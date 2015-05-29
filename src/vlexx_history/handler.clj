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
                [vlexx-history.dateutil :as dateutil]
                [clojure.tools.logging :as log]))

  (defn json-response [body]
    {:status  200
     :headers {"Content-Type" "application/json; charset=utf-8"}
     :body    body})

   (defn remove-id [response]
     (encode (map #(dissoc % :_id) response)))

   (defn get-current-trains []
     (json-response (remove-id (database/get-current-documents (clj-time.core/today)))))

   (defn get-delayed-top10 [date]
     (json-response (remove-id (database/get-delayed-top10 (dateutil/parse-date date)))))

   (defn get-stats []
     (json-response (database/get-stats)))

   (defroutes app-routes
      (context "/trains/current" [] (defroutes documents-routes
        (GET  "/" [] (get-current-trains))))

      (context "/trains/top10/:date" [date] (defroutes documents-routes
        (GET  "/" [] (get-delayed-top10 date))))

      (context "/trains/stats" [] (defroutes document-routes
        (GET "/" [] (get-stats))))

      (route/resources "/")
      (GET "/" [] (resource-response "index.html" {:root "public"}))

      (route/not-found "Page not found"))

   (def app
      (-> (handler/api app-routes)
        (middleware/wrap-json-response)))

   (defn start-timer []
     "Schedules the request to the vlexx server"
     (log/info "Init method was called")
     (task/schedule fetcher/check-delays))

