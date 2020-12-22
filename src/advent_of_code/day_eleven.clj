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
  (->> f
       io/resource
       slurp
       string/split-lines
       (mapv parse-row)))

(defn coord-in-range [[w h] [r c]]
  (and (<= 0 r (dec h))
       (<= 0 c (dec w))))

(defn get-neighbours [[w h :as size] [r c]]
  (->> directions
       (map #(map + [r c] %))
       (filter (partial coord-in-range size))))

(defn coords->value [grid [r c]]
  (-> grid (nth r) (nth c)))

(defn count-occupied-seats [v]
  (count (filter #{:O} v)))

(defn grid-size [grid]
  [(count (first grid)) (count grid)])

(defn assign-new-seats [grid]
  (let [[w h] (grid-size grid)]
    ;; maybe update in place, rather than create new 2d coll and track whether
    ;; we've changed anything
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

(defn seat-occupied-in-direction [grid [delta-r delta-c :as delta] [r c :as coord]]
  (let [new-coords (map + coord delta)]
    (if (coord-in-range (grid-size grid) new-coords)
      (let [seat-state (coords->value grid new-coords)]
        (case seat-state
          :O true
          :_ false
          (seat-occupied-in-direction grid delta new-coords)))
      false)))

(defn seats-occupied-in-all-directions [grid coord]
(->> directions
     (map #(seat-occupied-in-direction grid % coord))
     (filter identity)
     count))

(defn assign-new-seats-vis-seats [grid]
  (let [[w h]  (grid-size grid)]
    (->> (for [r (range h)
               c (range w)]
           (let [seat-state (coords->value grid [r c])]
             (if (#{:O :_} seat-state)
               (let [seats-occupied (seats-occupied-in-all-directions grid [r c])]
                 (case seat-state
                   :_ (if (zero? seats-occupied) :O :_)
                   :O (if (<= 5 seats-occupied) :_ :O)))
               :x)))
         (partition w))))

(defn get-day11-answer-pt2 [grid]
  (let [new-grid (assign-new-seats-vis-seats grid)]
    (if (= grid new-grid)
      (count-occupied-seats (flatten grid))
      (get-day11-answer-pt2 new-grid))))
