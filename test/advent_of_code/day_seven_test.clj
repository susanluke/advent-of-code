(ns advent-of-code.day-seven-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-seven :as sut]))

(deftest get-day7-answer-test
  (testing "Test Day 7"
    (is (= 124 (sut/get-day7-answer-pt1 :shiny-gold)))
    (is (= 34862 (sut/get-day7-answer-pt2)))))
