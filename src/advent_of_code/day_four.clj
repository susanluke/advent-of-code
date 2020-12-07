(ns advent-of-code.day-four
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day4-input.txt")

(defn parse-file []
  (->> (-> (io/resource f)
           slurp
           (string/split #"\n\n"))
       (map #(string/split %1 #"\n|\s"))
       (map data->passport-map)))

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

(defn valid-passport-pt2? [{:keys [byr iyr eyr hgt hcl ecl pid]}]
  (let [byr (read-string byr)
        iyr (read-string iyr)
        eyr (read-string eyr)
        [_ h-num-raw h-unit] (re-matches #"^(\d+)(cm|in)$" hgt)
        h-num (and h-num-raw (read-string h-num-raw))]
    (and (integer? byr) (<= 1920 byr 2002)
         (integer? iyr) (<= 2010 iyr 2020)
         (integer? eyr) (<= 2020 eyr 2030)
         (integer? h-num) (#{"cm" "in"} h-unit) (if (= "cm" h-unit)
                                                  (<= 150 h-num 193)
                                                  (<= 59 h-num 76))
         (re-matches #"^#[0-9a-f]{6}" hcl)
         (#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl)
         (re-matches #"[0-9]{9}" pid))))

(defn get-day4-answer-pt1
  []
  (->> (parse-file)
       (filter valid-passport-pt1?)
       count))

(defn get-day4-answer-pt2
  []
  (->> (parse-file)
       (filter valid-passport-pt1?)
       (filter valid-passport-pt2?)
       count))
