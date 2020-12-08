(ns advent-of-code.day-six-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-six :as sut]))

(deftest get-day6-answer-test
  (testing "Test Day 6"
    (is (= 6714 (sut/get-day6-answer-pt1)))
    (is (= 3435 (sut/get-day6-answer-pt2)))))
