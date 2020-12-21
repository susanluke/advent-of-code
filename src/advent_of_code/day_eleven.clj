(ns advent-of-code.day-eleven
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day11-input.txt")

(defn parse-row [s]
  (mapv #(case %
           \L :_  ; Empty chair
           \. :x  ; Floor
           \# :O) ; Occupied
        s))

(def seat-grid
  (->> (-> f
           io/resource
           slurp
           string/split-lines)
       (mapv parse-row)))

(defn get-neighbours [[w h] [r c]]
  (let [neighbours [[-1 -1] [-1 0] [-1 1]
                    [ 0 -1]        [ 0 1]
                    [ 1 -1] [ 1 0] [ 1 1]]]
    (->> neighbours
         (map #(map + [r c] %))
         (filter (fn [[r c]]
                   (and (<= 0 r (dec h))
                        (<= 0 c (dec w))))))))

(defn coords->value [grid [r c]]
  (-> grid
      (nth r)
      (nth c)))

(defn count-occupied-seats [v]
  ;; could switch to filter/count
  (reduce (fn [acc v]
            (if (= :O v)
              (inc acc)
              acc))
          0
          v))

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


(comment
  (def test-input
    "L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL")

  (def seat-grid-test
    (->> test-input string/split-lines (map parse-row))))
