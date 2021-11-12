(ns advent-of-code.day-sixteen-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day-sixteen :as sut]))

(deftest get-day16-answer-test
  (is (= 19087 (sut/get-day16-answer-pt1))))
