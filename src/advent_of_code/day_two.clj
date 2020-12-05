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

(defn valid-password-pt1?
  [{:keys [low hi letter password]}]
  (<= low
      (->> password
           (filter #(= letter %1))
           count)
      hi))

(defn valid-password-pt2?
  [{:keys [low hi letter password]}]
  (let [l1 (->> (dec low) (nth password) (= letter))
        l2 (->> (dec hi)  (nth password) (= letter))]
    (and (or l1 l2)
         (not (and l1 l2)))))

(defn get-day2-answer-pt1
  []
  (->> (parse-file)
       (filter valid-password-pt1?)
       count))

(defn get-day2-answer-pt2
  []
  (->> (parse-file)
       (filter valid-password-pt2?)
       count))
