(ns advent-of-code.day-four-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-four :as sut]))

(deftest get-day4-answer-test
  (testing "Test Day 4"
    (is (= 200 (sut/get-day4-answer-pt1)))
    (is (= 116 (sut/get-day4-answer-pt2)))))
