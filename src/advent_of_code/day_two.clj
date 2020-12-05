(ns advent-of-code.day-two
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day2-input.txt")

(defn parse-file
  []
  (->> (io/resource f)
       slurp
       string/split-lines
       (map #(re-matches #"^(\d+)-(\d+) ([a-z]): ([a-z]*)$" %1))
       (map #(hash-map :low      (read-string (nth %1 1))
                       :hi       (read-string (nth %1 2))
                       :letter   (-> (nth %1 3) first char)
                       :password (nth %1 4)))))

(defn valid-password?
  [{:keys [low hi letter password]}]
  (<= low
      (->> password
           (filter #(= letter %1))
           count)
      hi))

(defn valid-password-pt2?
  [{:keys [low hi letter password]}]
  (let [l1 (nth password (dec low))
        l2 (nth password (dec hi))]
    (and (or (= letter l1) (= letter l2))
         (not (and (= letter l1) (= letter l2))))))

(defn get-day2-answer-pt1
  []
  (->> (parse-file)
       (filter valid-password?)
       count))

(defn get-day2-answer-pt2
  []
  (->> (parse-file)
       (filter valid-password-pt2?)
       count))
