(ns advent-of-code.day-eleven-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-eleven :as sut]))

(deftest get-day11-answer-test
  (testing "Test Day 11"
    (is (= 2412 (sut/get-day11-answer-pt1 sut/seat-grid)))))
