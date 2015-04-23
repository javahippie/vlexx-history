  (ns vlexx-history.database
      (:gen-class)
      (:require [monger.core :as mg]
                [monger.collection :as mc]
                [clj-time.core :as t]
                [clj-time.format :as f]))

  (def conn (mg/connect))
  (def db (mg/get-db conn "vlexx-history"))
  (def coll "reports")


  (defn get-all-documents []
    "Returns a list of all trains in the database"
        (mc/find-seq db coll {}))

  (defn get-delayed-documents[]
    "Returns a list of all delayed trains in the database"
        (mc/find-seq db coll {:prognosemin {:$gt "0"}}))

  (defn save-result-in-db [auskuenfte]
    "Saves a list of (denormalized) trains to the database"
     (print(map #(mc/upsert db coll {:zug (:zug %) :zeit (:zeit %) :tag (:tag %)} %) auskuenfte))
     (mg/disconnect conn))
