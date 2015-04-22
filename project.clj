(defproject vlexx-history "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [ring/ring-json "0.1.2"]
                 [ring/ring-defaults "0.1.2"]
                 [com.novemberain/monger "2.1.0"]
                 [overtone/at-at "1.2.0"]
                 [clj-http "1.1.0"]
                 [cheshire "4.0.3"]
                 [clj-time "0.9.0"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler vlexx-history.handler/app
         :init vlexx-history.handler/start-timer}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
