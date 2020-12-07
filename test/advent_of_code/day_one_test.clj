(ns advent-of-code.day-one-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-one :as sut]))

(deftest get-day1-answer-test
  (testing "Test Day 1"
    (is (= 964875 (sut/get-day1-answer-pt1)))
    (is (= 158661360 (sut/get-day1-answer-pt2)))))
