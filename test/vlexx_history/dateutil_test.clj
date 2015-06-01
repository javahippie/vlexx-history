(ns vlexx-history.dateutil-test
  (:require [clojure.test :refer :all]
            [vlexx-history.dateutil :refer :all]
            [clj-time.core :as t]
            [clj-time.format :as f]))

(defn time-with-delta-as-string [delta]
  (let [curdate (now-in-germany)
        curhour (t/hour curdate)
        curminute (t/minute curdate)]
    (f/unparse-local-time (f/formatters :hour-minute) (t/local-time (+ curhour delta) curminute))))

(deftest test-parse-date
  (testing "Parse Date"
    (let [result (parse-date "2015-05-05")]
      (is (= (t/day result) 5))
      (is (= (t/month result) 5))
      (is (= (t/year result ) 2015)))))

(deftest test-parse-time
   (testing "Parse Time"
    (let [result (parse-time "00:10")]
      (is (= (t/minute result) 10))
      (is (= (t/hour result ) 0))
      (is (= (t/second result) 0)))))

(deftest test-parse-date-time
  (testing "Parse date time"
    (let [datstring "2015-01-01 22:20"
          result (parse-date-time datstring)]
      (is (= (t/year result) 2015))
      (is (= (t/month result) 1))
      (is (= (t/day result) 1))
      (is (= (t/hour result) 22))
      (is (= (t/minute result) 20)))))

(deftest test-merge-date-with-time
  (testing "Merge date with time"
    (let [dat (parse-date "2015-01-01")
          tim (parse-time "20:15")
          result (merge-date-with-time dat tim)]
      (is (= (t/day result) 1))
      (is (= (t/month result) 1))
      (is (= (t/year result ) 2015))
      (is (= (t/minute result) 15))
      (is (= (t/hour result ) 20))
      (is (= (t/second result) 0)))))

(deftest test-time-string-to-date-time
  (testing "Merging future time with date"
    (let [today (now-in-germany)
          time-string (time-with-delta-as-string 1)
          result (time-string-to-datetime time-string)]
    (is (=(t/day result) (t/day today)))))

  (testing "Merging current time with date"
    (let [today (now-in-germany)
          time-string (time-with-delta-as-string 0)
          result (time-string-to-datetime time-string)]
    (is (=(t/day result) (t/day today)))))

  (testing "Merging one hour ago with date"
    (let [tomorrow (tomorrow-in-germany)
          time-string (time-with-delta-as-string -1)
          result (time-string-to-datetime time-string)]
    (is (=(t/day result) (t/day tomorrow))))))
