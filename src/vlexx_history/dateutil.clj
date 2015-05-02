  (ns vlexx-history.dateutil
      (:gen-class)
      (:require [clj-time.core :as t]
                [clj-time.format :as f]))

(defn todays-date []
    "Returns todays date as string, due to my failed attempts to save a joda date in the database. To be continued"
    (f/unparse (f/formatters :date) (t/today-at-midnight)))

(defn current-time []
    "Returns todays date as string, due to my failed attempts to save a joda date in the database. To be continued"
    (f/unparse (f/formatters :hour-minute) (t/now)))

(defn now-in-germany []
  (t/to-time-zone (t/now) (t/time-zone-for-id "Europe/Berlin")))
