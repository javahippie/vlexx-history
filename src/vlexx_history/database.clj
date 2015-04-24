  (ns vlexx-history.database
      (:gen-class)
      (:refer-clojure :exclude [sort find])
      (:require [monger.core :as mg]
                [monger.collection :as mc]
                [monger.query :refer :all]
                [clojure.tools.logging :as log]))

  (def conn (mg/connect))
  (def db (mg/get-db conn "vlexx-history"))
  (def coll "reports")


  (defn get-all-documents []
    "Returns a list of all trains in the database"
        (log/info "Request to database/get-all-documents")
        (mc/find-maps db coll {}))

  (defn get-delayed-top10 [day]
    "Returns a list of the 10 most delayed trains"
     (log/info "Request to database/get-delayed-top10")
     (with-collection db coll
      (find {:tag day})
      (sort (array-map :prognosemin -1))
      (limit 10)))

  (defn save-result-in-db [auskuenfte]
    "Saves a list of (denormalized) trains to the database"
     (log/info "Request to save-result-in-db ")
     (log/info (map #(mc/upsert db coll {:zug (:zug %) :zeit (:zeit %) :tag (:tag %)} %) auskuenfte)))
