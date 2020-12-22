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

(def test-instructions (sut/parse-string test-input))

(deftest get-day12-answer-test
  (testing "Test Day 12"
    (is (= 25 (sut/get-manhattan-dist test-instructions)))
    (is (= 1007 (sut/get-day12-answer-pt1)))
    (is (= 286 (sut/get-manhattan-dist-waypoint-instructions test-instructions)))
    (is (= 41212 (sut/get-day12-answer-pt2)))))
