(ns advent-of-code.day-fourteen-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-fourteen :as sut]))

(def test-input
  "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
mem[8] = 11
mem[7] = 101
mem[8] = 0")

(def test-input2
  "mask = 000000000000000000000000000000X1001X
mem[42] = 100
mask = 00000000000000000000000000000000X0XX
mem[26] = 1")

(deftest get-day14-answer-test
  (testing "Test Day 14"
    (is (= 11501064782628 (sut/get-day14-answer-pt1)))
    (is (= 5142195937660 (sut/get-day14-answer-pt2)))
    (is (= 165 (sut/perform-instructions :v1 (sut/parse-string :v1 test-input))))
    (is (= 208 (sut/perform-instructions :v2 (sut/parse-string :v2 test-input2))))
    ))
