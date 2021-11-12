(ns advent-of-code.day-fifteen-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-fifteen :as sut]))

(deftest get-day15-answer-test
  (is (= 387 (sut/get-day15-answer-pt1)))

  (are [input output] (= output (sut/get-nth-val input 2020))
    "1,3,2" 1
    "2,1,3" 10
    "1,2,3" 27
    "2,3,1" 78
    "3,2,1" 438
    "3,1,2" 1836))
