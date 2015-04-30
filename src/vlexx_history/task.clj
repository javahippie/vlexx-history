  (ns vlexx-history.task
      (:gen-class)
      (:use overtone.at-at)
      (:require [clojure.tools.logging :as log]))

  (defn schedule [function-to-be-scheduled]
    "Scheduling the passed method to be executed every minute"
    (log/info "A recurring job was scheduled")
    (let [minute 60000
          tp (mk-pool)]
      (every minute function-to-be-scheduled tp)))
