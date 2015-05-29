(ns vlexx-history.typeutil-test
  (:require [clojure.test :refer :all]
            [vlexx-history.typeutil :refer :all]))

(deftest test-parse-int

  (testing "Parsing correct int representation"
    (let [result (parse-int "2")]
      (is (= result 2))))

  (testing "Parsing illegal int representation"
    (try (parse-int "a")
      (catch NumberFormatException e (is true)))))

