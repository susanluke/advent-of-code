(ns advent-of-code.day-fifteen
  (:require [clojure.string :as string]))

(def input "14,1,17,0,3,20")

(defn say-number [s n]
  (-> s
      (update-in [:nums n]
                 #(take 2 (conj % (:current-idx s))))
      (assoc :last-num n)
      (update :current-idx inc)))

(defn parse-input [s]
  (->> (string/split s #",")
       (map read-string)
       (reduce say-number {:current-idx 0 :nums {}})))

(defn game-round [{:keys [last-num nums] :as state}]
  (let [idxs (nums last-num)]
    (say-number state (if (<= 2 (count idxs))
                        (- (first idxs) (second idxs))
                        0))))

(defn get-nth-val [s n]
  (let [initial-state (parse-input s)]
    (-> (iterate game-round initial-state)
        (nth (- n (:current-idx initial-state)))
        :last-num)))

(defn get-day15-answer-pt1 []
  (get-nth-val input 2020))

(defn get-day15-answer-pt2 []
  (get-nth-val input 30000000))
