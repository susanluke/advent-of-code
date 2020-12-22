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
    :F (case facing
         0 (follow-direction state {:action :N :value value})
         90 (follow-direction state {:action :E :value value})
         180 (follow-direction state {:action :S :value value})
         270 (follow-direction state {:action :W :value value}))))

(defn abs [n] (max n (- n)))

(defn add-xy [{:keys [x y]}] (+ (abs x) (abs y)))

(defn get-day12-answer-pt1 []
  (-> (reduce follow-direction {:facing 90 :x 0 :y 0} (read-file))
      add-xy))
