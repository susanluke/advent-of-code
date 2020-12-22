(ns advent-of-code.day-twelve-test
  (:require [clojure.string :as string]
            [clojure.test :refer :all]
            [advent-of-code.day-twelve :as sut]))

(def test-input
  "F10
N3
F7
R90
F11")

(deftest get-day12-answer-test
  (testing "Test Day 12"
    (is (= 1007 (sut/get-day12-answer-pt1)))))
