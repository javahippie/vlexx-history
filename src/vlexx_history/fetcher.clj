  (ns vlexx-history.fetcher
      (:gen-class)
      (:require
                [clj-http.client :as client]
                [monger.core :as mg]
                [monger.collection :as mc]
                [clj-time.core :as t]
                [clj-time.format :as f]
                ))


  (defn todays-date []
    (f/unparse (f/formatters :date) (t/today-at-midnight)))


  (defn flatten-dataset [auskuenfte]
    (map #(assoc % :bahnhof (:name auskuenfte)
                   :stand   (:stand auskuenfte)
                   :tag     (todays-date)) (:abfahrt auskuenfte) ))

  (defn save-in-db [auskuenfte]
   (let [conn (mg/connect)
          db   (mg/get-db conn "vlexx-history")
          coll "reports"]
     (map #(mc/upsert db coll {:zug (:zug %) :zeit (:zeit %) :tag (todays-date)} %) auskuenfte)))


  (defn check-delays []
    (println "check-delays was called")
    (let [response (:body (client/get "http://www.vlexx.de/etc_data.php?type=stationsauskunft&bhf=FMZ" {:as :json}))]
      (save-in-db (flatten-dataset response))))


