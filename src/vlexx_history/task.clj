  (ns vlexx-history.task
      (:gen-class)
      (:use overtone.at-at))

  (def tp (mk-pool))

  (defn schedule [function-to-be-scheduled]
    (every 60000 function-to-be-scheduled tp))
