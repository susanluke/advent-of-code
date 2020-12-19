(ns advent-of-code.day-eight-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-eight :as sut]))

(deftest get-day8-answer-test
  (testing "Test Day 8"
    (is (= 1521 (sut/get-day8-answer-pt1)))))
