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
         (reduce (fn [num-counts n]
                   (case n
                     -1 (update num-counts 0 inc)
                     -3 (update num-counts 1 inc)
                     num-counts))
                 [1 1])
         (apply *))))

(def num-solns-from
  (memoize
   (fn [li i]
     (let [li-len (count li)]
       (if (= li-len (inc i))
         1
         (+ (if (and (> li-len (inc i))
                     (>= 3 (- (nth li (inc i)) (nth li i))))
              (num-solns-from li (inc i))
              0)
            (if (and (> li-len (+ i 2))
                     (>= 3 (- (nth li (+ i 2)) (nth li i))))
              (num-solns-from li (+ i 2))
              0)
            (if (and (> li-len (+ i 3))
                     (>= 3 (- (nth li (+ i 3)) (nth li i))))
              (num-solns-from li (+ i 3))
              0)))))))


(defn get-day10-answer-pt2 []
  (let [li (-> (read-file) (conj 0) sort vec)]
    (num-solns-from li 0)))
