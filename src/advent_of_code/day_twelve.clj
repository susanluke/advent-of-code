(ns advent-of-code.day-twelve
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day12-input.txt")

(defn parse-line [s]
  (let [[_ a v] (re-matches #"^([NSEWLRF])([0-9]+)$" s)]
    {:action (keyword a)
     :value (read-string v)}))

(defn parse-string [s]
  (->> s string/split-lines (map parse-line)))

(defn read-file []
  (-> f io/resource slurp parse-string))

(defn follow-direction [{:keys [facing x y] :as state}
                        {:keys [action value] :as instruction}]
  (case action
    :N (assoc state :y (+ y value))
    :S (assoc state :y (- y value))
    :E (assoc state :x (+ x value))
    :W (assoc state :x (- x value))
    :L (assoc state :facing (mod (- facing value) 360))
    :R (assoc state :facing (mod (+ facing value) 360))
    :F (let [new-dir (case facing 0 :N 90 :E 180 :S 270 :W)]
         (follow-direction state {:action new-dir :value value}))))

(defn abs [n] (max n (- n)))

(defn add-xy [{:keys [x y]}] (+ (abs x) (abs y)))

(defn get-manhattan-dist [instructions]
  (-> (reduce follow-direction {:facing 90 :x 0 :y 0} instructions)
      add-xy))

(defn get-day12-answer-pt1 []
  (get-manhattan-dist (read-file)))

(defn rotate-waypoint-right-90 [{:keys [wx wy] :as state}]
  (-> state
      (assoc :wx wy)
      (assoc :wy (- wx))))

(defn rotate-waypoint-right [state {:keys [value] :as instruction}]
  (nth (iterate rotate-waypoint-right-90 state)
       (quot value 90)))

(defn follow-direction-with-waypoint [{:keys [wx wy x y] :as state}
                                      {:keys [action value] :as instruction}]
  (case action
    :N (assoc state :wy (+ wy value))
    :S (assoc state :wy (- wy value))
    :E (assoc state :wx (+ wx value))
    :W (assoc state :wx (- wx value))
    :L (rotate-waypoint-right state {:action :R
                                     :value  (mod (- value) 360)})
    :R (rotate-waypoint-right state instruction)
    :F (-> state
           (assoc :x (+ (* value wx) x))
           (assoc :y (+ (* value wy) y)))))

(defn get-manhattan-dist-waypoint-instructions [instructions]
  (-> (reduce follow-direction-with-waypoint {:wx 10 :wy 1 :x 0 :y 0} instructions)
      add-xy))

(defn get-day12-answer-pt2 []
  (get-manhattan-dist-waypoint-instructions (read-file)))
