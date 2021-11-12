(ns advent-of-code.day-sixteen
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def f     "day16-input.txt")
(def input (-> f io/resource slurp))

(defn split-range [s]
  (let [[_ lo hi] (re-matches #"^(\d+)-(\d+)$" s)]
    (map read-string [lo hi])))

(defn parse-rule [s]
  (let [[_ name range1 range2] (re-matches #"^([\w\s]+): (\d+-\d+) or (\d+-\d+)$" s)]
    [(-> name (string/replace #"\s" "-" ) keyword)
     (split-range range1)
     (split-range range2)]))

(defn parse-ticket [s]
  (map read-string (string/split s #",")))

(defn parse-tickets [s]
  (->> s
       string/split-lines
       (drop 1)
       (map parse-ticket)))

(defn parse-input [s]
  (let [[rules my-ticket nearby-tickets] (string/split s #"\n\n")]
    {:rules          (map parse-rule (string/split-lines rules))
     :my-ticket      (first (parse-tickets my-ticket))
     :nearby-tickets (parse-tickets nearby-tickets)}))

(defn matches-rule? [[name [lo1 hi1] [lo2 hi2]] n]
  (or (<= lo1 n hi1)
      (<= lo2 n hi2)))

(defn fails-all-rules? [rules n]
  (every? #(not (matches-rule? % n)) rules))

(defn filter-numbers-breaking-rules [rules ticket]
  (filter (partial fails-all-rules? rules) ticket))


(defn get-day16-answer-pt1 []
  (let [{:keys [rules my-ticket nearby-tickets]} (parse-input input)]
    (->> nearby-tickets
         (map (partial filter-numbers-breaking-rules rules))
         (map (partial apply + ))
         (apply +))))

(defn get-day16-answer-pt2 []
  (let [{:keys [rules my-ticket nearby-tickets]} (parse-input input)]
    (->> nearby-tickets



         )))
