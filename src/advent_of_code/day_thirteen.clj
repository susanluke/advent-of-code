(ns advent-of-code.day-thirteen
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day13-input.txt")
(def input-string (-> f io/resource slurp))


(defn parse-string [s]
  (let [[ts bs] (string/split-lines s)
        t       (read-string ts)
        buses   (->> (string/split bs #",")
                     (remove #{"x"})
                     (map read-string))]
    [t buses]))

(defn get-earliest-times-for-buses [t buses]
  (map (fn [bus] {:bus           bus
                 :et (- bus (mod t bus))})
       buses))

(defn get-earliest-bus [bus-times]
  (reduce (fn [prev-bus new-bus]
            (if (< (:et new-bus) (:et prev-bus))
              new-bus
              prev-bus))
          bus-times))

(defn get-day13-answer-pt1 []
  (let [[t buses] (parse-string input-string)]
    (->> (get-earliest-times-for-buses t buses)
         get-earliest-bus
         vals
         (apply *))))

(defn parst-string-for-pt2 [s]
  )
