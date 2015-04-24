  (ns vlexx-history.typeutil
      (:gen-class))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))
