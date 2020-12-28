(ns advent-of-code.day-thirteen-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-thirteen :as sut]))

(def test-input "939
7,13,x,x,59,x,31,19")

(deftest get-day13-answer-test
  (testing "Test Day 13"
    (is (= 295 (sut/get-earliest-bus-from-string test-input)))
    (is (= 1068781 (sut/get-earliest-time-buses-have-correct-offset test-input)))

    (is (= 370 (sut/get-day13-answer-pt1)))
    (is (= 894954360381385 (sut/get-day13-answer-pt2)))))
