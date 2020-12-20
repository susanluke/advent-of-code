(ns advent-of-code.day-ten
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day10-input.txt")

(defn read-file []
  (->> (-> f
           io/resource
           slurp
           string/split-lines)
       (map read-string)))

(defn get-day10-answer-pt1 []
  (let [li (-> (read-file) sort)]
    (->> (partition 2 1 li)
         (map #(apply - %))
         (reduce (fn [[num-1s num-3s] n]
                   (case n
                     -1 [(inc num-1s) num-3s]
                     -3 [num-1s (inc num-3s)]
                     [num-1s num-3s]))
                 [1 1])
         (apply *))))
