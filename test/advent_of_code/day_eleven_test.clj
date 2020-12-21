(ns advent-of-code.day-eleven-test
  (:require [clojure.test :refer :all]
            [clojure.string :as string]
            [advent-of-code.day-eleven :as sut]))

(def test-input
  "L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL")

(def test-seat-grid
  (->> test-input string/split-lines (map sut/parse-row)))

(deftest get-day11-answer-test
  (testing "Test Day 11"
    ;; This takes a while to run
    ;;(is (= 2412 (sut/get-day11-answer-pt1 sut/seat-grid)))
    (is (= 37 (sut/get-day11-answer-pt1 test-seat-grid)))
    ))
