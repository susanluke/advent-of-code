(ns advent-of-code.day-nine-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-nine :as sut]))

(deftest get-day9-answer-test
  (testing "Test Day 9"
    (is (= 373803594 (sut/get-day9-answer-pt1)))
    (is (= 51152360 (sut/get-day9-answer-pt2)))))
