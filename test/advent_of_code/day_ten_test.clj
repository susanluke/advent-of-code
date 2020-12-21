(ns advent-of-code.day-ten-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-ten :as sut]))

(deftest get-day10-answer-test
  (testing "Test Day 10"
    (is (= 2775 (sut/get-day10-answer-pt1)))
    (is (= 518344341716992 (sut/get-day10-answer-pt2)))))
