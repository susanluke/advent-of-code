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

(defn get-day8-answer-pt1 []
  (perform-instruction (read-file) 0 0))

(defn perform-instruction-with-correction [instructions acc i allow-flips]
  ;;(println "-> " i acc)
  (if (= i (count instructions))
    acc
    (if (not (<= 0 i (count instructions)))
      :ioob
      (let [[op n seen] (nth instructions i)
            new-instructions (assoc instructions i [op n true])]
        (if seen
          :inf-loop
          (do
            ;;(println "   " i acc op n)
            (case op
              :nop (let [attempt-1 (perform-instruction-with-correction new-instructions acc (inc i) allow-flips)]
                     (if (and (#{:ioob :inf-loop} attempt-1) allow-flips)
                       (recur new-instructions acc (+ n i) false) ;; try jump
                       attempt-1))
              :acc (recur new-instructions (+ acc n) (inc i) allow-flips)
              :jmp (let [attempt-1 (perform-instruction-with-correction new-instructions acc (+ n i) allow-flips)]
                     (if (and (#{:ioob :inf-loop} attempt-1) allow-flips)
                       (recur new-instructions acc (inc i) false) ;; try noop
                       attempt-1)))))))))

(defn get-day8-answer-pt2 []
  (perform-instruction-with-correction (read-file) 0 0 true))
