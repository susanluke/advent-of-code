(ns advent-of-code.day-five-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-five :as sut]))

(deftest get-day5-answer-test
  (testing "Test Day 5"
    (is (= 901 (sut/get-day5-answer-pt1)))))
