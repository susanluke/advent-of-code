(ns advent-of-code.day-fourteen-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-fourteen :as sut]))

(deftest get-day14-answer-test
  (testing "Test Day 14"
    (is (= 11501064782628 (sut/get-day14-answer-pt1)))))
