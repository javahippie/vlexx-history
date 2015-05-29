  (ns vlexx-history.fetcher
      (:gen-class)
      (:use vlexx-history.dateutil)
      (:use vlexx-history.typeutil)
      (:require [vlexx-history.database :as database]
                [clj-http.client :as client]
                [clj-time.core :as t]
                [monger.joda-time]
                [clojure.tools.logging :as log]))

  (defn flatten-dataset [auskuenfte]
    "Denormalizes the request objects and adds todays date, so that a unique key can be created"
    (map #(assoc % :bahnhof (:name auskuenfte)
                   :stand   (:stand auskuenfte)
                   :prognosemin (parse-int (:prognosemin %))
                   :zeit (time-string-to-datetime (:zeit %))) (:abfahrt auskuenfte)))

  (defn check-delays []
    "Fetches datasets from the Vlexx servers and calls a routine for storing in database"
    (log/info "Loading data from vlexx")
    (try
      (let
        [response (:body (client/get "http://www.vlexx.de/etc_data.php?type=stationsauskunft&bhf=FMZ" {:as :json}))]
        (database/save-result-in-db (flatten-dataset response)))
      (catch Exception e (log/error "Error while updating trains" e))))
