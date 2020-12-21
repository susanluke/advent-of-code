(ns advent-of-code.day-eleven
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day11-input.txt")

(def directions [[-1 -1] [-1 0] [-1 1]
                 [ 0 -1]        [ 0 1]
                 [ 1 -1] [ 1 0] [ 1 1]])

(defn parse-row [s]
  (mapv #(case %
           \L :_                        ; Empty chair
           \. :x                        ; Floor
           \# :O)                       ; Occupied
        s))

(def seat-grid
  (->> (-> f
           io/resource
           slurp
           string/split-lines)
       (mapv parse-row)))

(defn get-neighbours [[w h] [r c]]
  (let []
    (->> directions
         (map #(map + [r c] %))
         (filter (fn [[r c]]
                   (and (<= 0 r (dec h))
                        (<= 0 c (dec w))))))))

(defn coords->value [grid [r c]]
  (-> grid (nth r) (nth c)))

(defn count-occupied-seats [v]
  (count (filter #{:O} v)))

(defn assign-new-seats [grid]
  (let [h (count grid)
        w (count (first grid))]
    (->> (for [r (range h)
               c (range w)]
           (let [current-state (coords->value grid [r c])]
             (if (= current-state :x)
               :x
               (->> (get-neighbours [w h] [r c])
                    (map (partial coords->value grid))
                    count-occupied-seats
                    (#(case current-state
                        :_ (if (zero? %) :O :_)
                        :O (if (<= 4 %) :_ :O)))))))
         (partition w))))

(defn get-day11-answer-pt1 [grid]
  (let [new-grid (assign-new-seats grid)]
    (if (= grid new-grid)
      (count-occupied-seats (flatten grid))
      (get-day11-answer-pt1 new-grid))))
