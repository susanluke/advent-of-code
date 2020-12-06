(ns advent-of-code.day-three
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day3-input.txt")

(defn read-file []
  (->> (io/resource f)
       slurp
       string/split-lines))

(defn count-trees-down-slope
  [r-grad d-grad f]
  (->> f
       (take-nth d-grad)
       (map-indexed #(if (= (nth %2 (mod (* r-grad %1)
                                         (count %2)))
                            \#)
                       1 0))
       (apply +)))

(defn get-day3-answer-pt1
  []
  (count-trees-down-slope 3 1 (read-file)))

(defn get-day3-answer-pt2
  []
  (let [f (read-file)]
    (* (count-trees-down-slope 1 1 f)
       (count-trees-down-slope 3 1 f)
       (count-trees-down-slope 5 1 f)
       (count-trees-down-slope 7 1 f)
       (count-trees-down-slope 1 2 f))))
