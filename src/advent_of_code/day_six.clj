(ns advent-of-code.day-six
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :as set]))

(def f "day6-input.txt")

(defn parse-file []
  (->> (-> (io/resource f)
           slurp
           (string/split #"\n\n"))
       (map #(string/split %1 #"\n"))))

(defn get-day6-answer-pt1 []
  (->> (parse-file)
       (map (comp count set (partial apply str)))
       (apply +)))

(defn get-day6-answer-pt2 []
  (->> (parse-file)
       (map (comp count (partial apply set/intersection) (partial map set)))
       (apply +)))
