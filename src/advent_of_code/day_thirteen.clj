(ns advent-of-code.day-thirteen
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day13-input.txt")
(def input-string (-> f io/resource slurp))

(defn parse-string [s]
  (let [[ts bs] (string/split-lines s)
        t       (read-string ts)
        buses   (->> (string/split bs #",")
                     (map read-string)
                     (map-indexed #(hash-map :offset % :bus %2)))]
    [t buses]))

(defn get-earliest-times-for-buses [t buses]
  (map (fn [bus] {:bus bus :et (- bus (mod t bus))}) buses))

(defn get-earliest-bus [bus-times]
  (reduce (fn [prev-bus new-bus]
            (if (< (:et new-bus) (:et prev-bus)) new-bus prev-bus))
          bus-times))

(defn get-earliest-bus-from-string [s]
  (let [[t buses]   (parse-string s)
        non-x-buses (->> buses
                         (remove #(= 'x (:bus %)))
                         (map :bus))]
    (->> (get-earliest-times-for-buses t non-x-buses)
         get-earliest-bus
         vals
         (apply *))))

(defn get-day13-answer-pt1 []
  (get-earliest-bus-from-string input-string))

(defn time-and-frequency-bus-has-offset
  [[t freq] {:keys [offset bus]}]
  ;; Find offsets at range of times
  (let [num-freqs (->> (iterate (partial + freq) t)
                       (map-indexed #(vector %1 (- bus (mod %2 bus))))
                       ;; offset must be modulo bus-num incase offset > bus
                       (filter #(= (mod offset bus) (second %)))
                       ffirst)]
    ;; Return in format so we can reduce over bus list.  Since buses are all
    ;; prime numbers, we know that they will offset every poss number and this
    ;; offset won't appear again until freq * bus-freq
    [(+ t (* num-freqs freq)) (* freq bus)]))

(defn get-earliest-time-buses-have-correct-offset [s]
  (let [[_ buses]       (parse-string s)
        first-bus       (first buses)
        non-x-buses     (remove #(= 'x (:bus %)) buses)]
    (->> (rest non-x-buses)
         (reduce time-and-frequency-bus-has-offset [0 (:bus first-bus)])
         first)))

(defn get-day13-answer-pt2 []
  (get-earliest-time-buses-have-correct-offset input-string))
