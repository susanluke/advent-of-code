(ns advent-of-code.core
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "/Users/sluke/git/advent-of-code/resources/day1-input.txt")
(def f "day1-input.txt")

(defn get-list
  "Retrieves file and parses into list"
  [f]
  (->> (io/resource f)
       slurp
       string/split-lines
       (map read-string)))

(defn target-sum-elements
  "Finds two elements of a list which add up to a target."
  [target input-list]
  (let [li (sort input-list)]
    (loop [l 0
           r (dec (count li))]
      (let [diff (- target (+ (nth li l)
                              (nth li r)))]
        (cond
          (= l r) :no-solution
          (zero? diff) (* (nth li l) (nth li r))
          (pos? diff) (recur (inc l) r)
          :else (recur l (dec r)))))))

(defn get-day1-answer
  []
  (let [input-list (get-list f)]
    (target-sum-elements 2020 input-list)))


(defn check-meaning-of-life
  "Humouring Amy"
  []
  42)

(comment
  (check-elements 1 2 [5 4 2 3 2])
  (foo [1 2 3]))
