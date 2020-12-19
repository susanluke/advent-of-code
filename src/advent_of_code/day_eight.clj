(ns advent-of-code.day-eight
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day8-input.txt")

(defn parse-line [s]
  (let [[op n] (string/split s #"\s")]
    [(keyword op) (read-string n) false]))

(defn read-file []
  (->> (-> (io/resource f)
           slurp
           string/split-lines)
       (map parse-line)
       vec))

(defn perform-instruction [instructions acc i]
  (let [[op n seen] (nth instructions i)
        new-instructions (assoc instructions i [op n true])]
    (if seen
      acc
      (case op
        :nop (recur new-instructions acc (inc i))
        :acc (recur new-instructions (+ acc n) (inc i))
        :jmp (recur new-instructions acc (+ n i))))))

(defn get-day8-answer-pt1
  []
  (perform-instruction (read-file) 0 0))
