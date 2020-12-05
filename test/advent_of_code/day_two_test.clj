(ns advent-of-code.day-two-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-two :as sut]))

(deftest get-day2-answer-test
  (testing "Test Day 2"
    (is (= 666 (sut/get-day2-answer)))))
