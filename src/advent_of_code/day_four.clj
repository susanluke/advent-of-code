(ns advent-of-code.day-four
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day4-input.txt")

(defn data->passport-map [v]
  (->> v
       (map (partial re-matches #"^(\w*):([\w#]*)$"))
       (map #(list (keyword(nth % 1)) (nth % 2)))
       flatten
       (apply hash-map)))

(defn valid-passport-pt1? [p]
  (every? (partial contains? p)
          ;; exclude :cid
          [:byr :iyr :eyr :hgt :hcl :ecl :pid]))

(defn parse-file []
  (->> (-> (io/resource f)
           slurp
           (string/split #"\n\n"))
       (map #(string/split %1 #"\n|\s"))
       (map data->passport-map)))

(defn get-day4-answer-pt1
  []
  (->> (parse-file)
       (filter valid-passport-pt1?)
       count))

(defn get-day4-answer-pt2
  []
  (->> (parse-file)
       ;; add filter
       count
       ))
