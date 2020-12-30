(ns advent-of-code.day-fifteen
  (:require [clojure.string :as string]))

(def input "14,1,17,0,3,20")

(defn say-number [s n]
  (-> s
      (update-in [:nums n]
                 (fnil #(conj % (:current-idx s)) '()))
      (assoc :last-num n)
      (update :current-idx inc)))

(defn parse-input [s]
  (->> (string/split s #",")
       (map read-string)
       (reduce say-number
               {:last-num    nil
                :current-idx 0
                :nums        {}})))

(defn game-round [{:keys [last-num nums] :as state}]
  (let [idxs      (nums last-num)
        new-num (if (<= 2 (count idxs))
                  (- (first idxs) (second idxs))
                  0)]
    (say-number state new-num)))

(defn get-2020th-val [s]
  (let [initial-state (parse-input s)
        initial-idx   (:current-idx initial-state)]
    (-> (iterate game-round initial-state)
        (nth (- 2020 initial-idx))
        :last-num)))

(defn get-day15-answer-pt1 []
  (get-2020th-val input))
