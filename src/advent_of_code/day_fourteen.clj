(ns advent-of-code.day-fourteen
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f       "day14-input.txt")
(def input   (-> f io/resource slurp))
(def mask-re #"mask = ([10X]+)")
(def mem-re  #"mem\[([0-9]+)\] = ([0-9]+)")

(defn s->mask [s x-replacement]
  (read-string (str "2r" (string/replace s #"X" x-replacement))))

(defn s->instruction [s]
  (if-let [[_ mask] (re-matches mask-re s)]
    [:mask {:and-mask (s->mask mask "1")
            :or-mask  (s->mask mask "0")}]
    (if-let [[_ mem-loc val] (re-matches mem-re s)]
      [:mem (read-string mem-loc) (read-string val)])))

(defn parse-string [s]
  (->> (string/split-lines s)
       (map s->instruction)))

(defn apply-mask [{:keys [and-mask or-mask]} v]
  (-> v (bit-and and-mask) (bit-or or-mask)))

(defn perform-instruction [{:keys [mask] :as state} [cmd :as instruction]]
  (case cmd
    :mask (assoc state :mask (second instruction))
    :mem  (let [[l v] (rest instruction)]
            (assoc-in state [:memory l] (apply-mask mask v)))))

(defn perform-instructions [instructions]
  (->> instructions
       (reduce perform-instruction {})
       :memory
       vals
       (apply +)))

(defn get-day14-answer-pt1 []
  (perform-instructions (parse-string input)))
