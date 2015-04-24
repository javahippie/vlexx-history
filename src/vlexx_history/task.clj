  (ns vlexx-history.task
      (:gen-class)
      (:use overtone.at-at)
      (:require [clojure.tools.logging :as log]))

  (def tp (mk-pool))

  (defn schedule [function-to-be-scheduled]
    (log/info "A recurring job was scheduled")
    (every 60000 function-to-be-scheduled tp))
