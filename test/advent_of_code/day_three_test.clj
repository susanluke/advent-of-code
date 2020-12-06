(ns advent-of-code.day-three-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-three :as sut]))

(deftest get-day3-answer-test
  (testing "Test Day 3"
    (is (= 247 (sut/get-day3-answer-pt1)))
    (is (= 2983070376 (sut/get-day3-answer-pt2)))))
