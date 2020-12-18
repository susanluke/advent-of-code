(ns advent-of-code.day-seven
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "day7-input.txt")

(defn read-file []
  (->> (io/resource f)
       slurp
       (string/split-lines)))

(defn bag-string->keyword [s]
  (-> s
      (string/replace #"\s" "-")
      keyword))

(defn parse-bag-data
  [s]
  (if (= "no other bags" s)
    []
    (let [[_ num bag] (re-matches #"^\s*([0-9]*) ([a-z ]+) bags?$" s)
          n (read-string num)
          b (bag-string->keyword bag)]
      [n b])))

(defn parse-contents
  "Takes a string of the form
  `1 shiny magenta bag, 2 muted teal bags, 1 muted chartreuse bag`
  returns list of vectors in the form
  ([1 :shiny-magenta]
   [2 :muted-teal]
   [1 :muted-chartreuse])"
  [s]
  (->> (string/split s #",")
       (map parse-bag-data)))

(defn parse-line
  [s]
  (let [[_ outer-bag inner-bags] (re-matches #"^([a-z ]*) bags contain (.*)\." s)
        ob (bag-string->keyword outer-bag)
        ibs (parse-contents inner-bags)]
    [ob ibs]))

(defn add-rule-to-bag-hold [m [outer-bag inner-bags]]
  (reduce (fn [m [_ inner-bag]]
            (update m
                    inner-bag
                    (fnil conj #{}) outer-bag))
          m
          inner-bags))

(defn holds-bag
  [bag-hold-rules bags-found bag]
  (let [bags (bag bag-hold-rules)] ;; ignore bags we've seen
    (reduce (fn [bags-found bag]
              (if-not (bags-found bag)
                (let [bags-found (conj bags-found bag)]
                  (set (concat bags-found (holds-bag bag-hold-rules bags-found bag))))
                bags-found))
            bags-found
            bags)))

(defn get-day7-answer-pt1 [bag-colour]
  (let [bag-contain-rules (->> (read-file)
                               (map parse-line))
        bag-hold-rules (reduce add-rule-to-bag-hold {} bag-contain-rules)]
    (-> (holds-bag bag-hold-rules #{} bag-colour)
        count)))
