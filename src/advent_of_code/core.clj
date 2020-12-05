(ns advent-of-code.core
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day1-input.txt")

(defn get-list
  "Retrieves file and parses into list"
  [f]
  (->> (io/resource f)
       slurp
       string/split-lines
       (map read-string)
       sort))

(defn target-sum-elements
  "Finds two elements of a list which add up to a target."
  [target input-list]
  (let [li input-list]
    (loop [l 0
           r (dec (count li))]
      (let [diff (- target (+ (nth li l)
                              (nth li r)))]
        (cond
          (= l r)      :no-solution
          (zero? diff) [(nth li l) (nth li r)]
          (pos? diff)  (recur (inc l) r)
          :else        (recur l (dec r)))))))

(defn get-day1-answer-pt1
  []
  ;; Get 2 elements that sum to target, then multiply them
  (let [input-list (get-list f)]
    (apply * (target-sum-elements 2020 input-list))))

(defn get-day1-answer-pt2
  []
  ;; Get 3 elements that sum to target, then multiply them
  (apply * (let [input-list (get-list f)
                 target     2020]
             (loop [li input-list]
               (let [x   (first input-list)
                     xs  (rest input-list)
                     res (target-sum-elements (- target x) xs)]
                 (if (not= :no-solution res)
                   (conj res x)
                   (recur xs)))))))
