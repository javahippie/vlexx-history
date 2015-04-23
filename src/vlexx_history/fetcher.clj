  (ns vlexx-history.fetcher
      (:gen-class)
      (:use vlexx-history.dateutil)
      (:require [vlexx-history.database :as database]
                [clj-http.client :as client]))

  (defn flatten-dataset [auskuenfte]
    "Denormalizes the request objects and adds todays date, so that a unique key can be created"
    (map #(assoc % :bahnhof (:name auskuenfte)
                   :stand   (:stand auskuenfte)
                   :tag     (todays-date)) (:abfahrt auskuenfte)))

  (defn check-delays []
    "Fetches datasets from the Vlexx servers and calls a routine for storing in database"
    (let
      [response (:body (client/get "http://www.vlexx.de/etc_data.php?type=stationsauskunft&bhf=FMZ" {:as :json}))]
      (print(database/save-result-in-db (flatten-dataset response)))))
