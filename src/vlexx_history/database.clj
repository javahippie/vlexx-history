  (ns vlexx-history.database
      (:gen-class)
      (:refer-clojure :exclude [sort find])
      (:require [monger.core :as mg]
                [monger.collection :as mc]
                [monger.query :refer :all]))

  (def conn (mg/connect))
  (def db (mg/get-db conn "vlexx-history"))
  (def coll "reports")


  (defn get-all-documents []
    "Returns a list of all trains in the database"
        (mc/find-seq db coll {}))

  (defn get-delayed-top10 [day]
    "Returns a list of the 10 most delayed trains"
     (with-collection db coll
      (find {:tag day})
      (sort (array-map :prognosemin 1))
      (limit 10)))

  (defn save-result-in-db [auskuenfte]
    "Saves a list of (denormalized) trains to the database"
     (print(map #(mc/upsert db coll {:zug (:zug %) :zeit (:zeit %) :tag (:tag %)} %) auskuenfte))
     (mg/disconnect conn))
