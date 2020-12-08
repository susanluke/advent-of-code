(ns advent-of-code.day-five
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day5-input.txt")

(defn read-file []
  (->> (io/resource f)
       slurp
       (string/split-lines)))

(defn seat-code->seat-id [seat-code]
  (let [row (->> seat-code (take 7) (map {\F \0 \B \1}) (apply str) (str "2r") read-string)
        col (->> seat-code (drop 7) (map {\L \0 \R \1}) (apply str) (str "2r") read-string)]
    (+ (* 8 row) col)))

(defn get-day5-answer-pt1 []
  (->> (read-file)
       (map seat-code->seat-id)
       (apply max)))
