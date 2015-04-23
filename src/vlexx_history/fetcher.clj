  (ns vlexx-history.fetcher
      (:gen-class)
      (:require [vlexx-history.database :as database]
                [clj-http.client :as client]
                [clj-time.core :as t]
                [clj-time.format :as f]))

  (defn todays-date []
    "Returns todays date as string, due to my failed attempts to save a joda date in the database. To be continued"
    (f/unparse (f/formatters :date) (t/today-at-midnight)))

  (defn flatten-dataset [auskuenfte]
    "Denormalizes the request objects and adds todays date, so that a unique key can be created"
    (map #(assoc % :bahnhof (:name auskuenfte)
                   :stand   (:stand auskuenfte)
                   :tag     (todays-date)) (:abfahrt auskuenfte) ))

  (defn check-delays []
    "Fetches datasets from the Vlexx servers and calls a routine for storing in database"
    (let
      [response (:body (client/get "http://www.vlexx.de/etc_data.php?type=stationsauskunft&bhf=FMZ" {:as :json}))]
      (print(database/save-result-in-db (flatten-dataset response)))))
