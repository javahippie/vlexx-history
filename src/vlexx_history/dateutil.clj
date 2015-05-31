   (ns vlexx-history.dateutil
      (:gen-class)
      (:require [clj-time.core :as t]
                [clj-time.format :as f]))

(defn to-german-timezone [dat]
  (t/to-time-zone dat (t/time-zone-for-id "Europe/Berlin")))

(defn now-in-germany []
  "Returns the current date in germany"
  (to-german-timezone (t/now)))

(defn tomorrow-in-germany []
  "Returns the current date + 1 in germany"
  (let [now (now-in-germany)
        one-day (t/days 1)]
    (t/plus now one-day)))

(defn parse-date [string-rep]
  "Parses a date string to date format"
  (to-german-timezone (f/parse-local-date (f/formatters :date) string-rep)))

(defn parse-date-time [string-rep]
  "Parses a date string to datetime format"
  (f/parse-local (f/formatters :date) string-rep))

(defn parse-time [string-rep]
  "Parses a time string to time format"
  (f/parse-local-time (f/formatters :hour-minute) string-rep))

(defn is-time-in-past? [check-time tolerance]
  "Checks, if the given time is in the past (for today). Tolerance in hours"
  (t/before? check-time (t/plus (t/time-now) (t/hours tolerance))))

(defn merge-date-with-time [date time]
  "Merges a date and a time. I assume there are better ways to do this, but i am at a train right now an can not look them up"
  (let [year (t/year date)
        month (t/month date)
        day (t/day date)
        hour (t/hour time)
        minute (t/minute time)]
  (t/date-time year month day hour minute)))

(defn time-string-to-datetime [time-string]
  "Receives a time string -for a time in future- and adds todays or tomorrows date to it"
  (let [time (parse-time time-string)]
    (if (is-time-in-past? time -1)
      (merge-date-with-time (tomorrow-in-germany) time)
      (merge-date-with-time (now-in-germany) time))))
