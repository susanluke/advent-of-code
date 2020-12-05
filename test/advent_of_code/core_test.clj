(ns advent-of-code.core-test
  (:require [clojure.test :refer :all]
            [advent-of-code.core :as sut]))

(deftest get-day1-answer-test
  (testing "Test Day 1"
    (is (= 964875 (sut/get-day1-answer)))))
