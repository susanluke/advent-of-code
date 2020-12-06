(ns advent-of-code.day-three
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day3-input.txt")

(defn read-file []
  (->> (io/resource f)
       slurp
       string/split-lines))

(defn get-day3-answer-pt1
  []
  (->> (read-file)
       (map-indexed #(if (= (nth %2 (mod (* 3 %1)
                                         (count %2)))
                            \#)
                       1 0))
       (apply +)))
