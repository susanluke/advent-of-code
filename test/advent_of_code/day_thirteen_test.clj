(ns advent-of-code.day-thirteen-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-thirteen :as sut]))

(deftest get-day13-answer-test
  (testing "Test Day 13"
    (is (= 370 (sut/get-day13-answer-pt1)))))
