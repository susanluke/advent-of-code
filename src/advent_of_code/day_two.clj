(ns advent-of-code.day-two
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day2-input.txt")

(defn read-file
  []
  (->> (io/resource f)
       slurp
       string/split-lines))

(defn valid-password?
  [{:keys [low hi letter password]}]
  (<= low
      (-> (filter #(= (-> letter first char) %1) password)
          count)
      hi))

(defn get-day2-answer
  []
  (->> (read-file)
       (map #(re-matches #"^(\d+)-(\d+) ([a-z]): ([a-z]*)$" %1))
       (map #(hash-map :low      (read-string (nth %1 1))
                       :hi       (read-string (nth %1 2))
                       :letter   (nth %1 3)
                       :password (nth %1 4)))
       (filter valid-password?)
       count))
