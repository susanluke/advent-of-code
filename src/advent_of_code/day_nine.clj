(ns advent-of-code.day-nine
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day9-input.txt")
(def input-string (-> f io/resource slurp))

(defn parse-string [s]
  (->> (string/split-lines s)
       (map read-string)
       vec))

(defn target-sum?
  [li target]
  (loop [l 0
         r (dec (count li))]
    (let [diff (- target (+ (nth li l)
                            (nth li r)))]
      (cond
        (= l r)      false
        (zero? diff) true
        (pos? diff)  (recur (inc l) r)
        :else        (recur l (dec r))))))

(defn first-not-sum-in-preamble
  [preamble-size v]
  (reduce #(if (target-sum? (-> v
                                (subvec (first %1) (second %1))
                                sort)
                            %2)
             (map inc %1)
             (reduced %2))
          [0 preamble-size]
          (drop preamble-size v)))

(defn get-day9-answer-pt1 []
  (first-not-sum-in-preamble 25 (parse-string input-string)))

(def invalid-number 373803594)

(defn get-day9-answer-pt2 []
  (let [all-nums     (parse-string input-string)
        target       invalid-number
        [si ei]      (loop [si 0
                            ei 2]
                       (let [tot (apply + (subvec all-nums si ei))]
                         (if (= tot target)
                           [si ei]
                           (if (and (> target tot)
                                    (> (count all-nums) ei))
                             (recur si (inc ei))
                             (recur (inc si) (+ si 3))))))
        list-for-sum (subvec all-nums si ei)
        li-min       (apply min list-for-sum)
        li-max       (apply max list-for-sum)]
    (+ li-min li-max)))
