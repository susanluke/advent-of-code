(ns advent-of-code.core
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f "/Users/sluke/git/advent-of-code/resources/day1-input.txt")

(defn check-elements [s e l]
  (println "checking " s " : " e " => " (- 2020 (+ (nth l s)
                                                   (nth l e))))
  (- 2020 (+ (nth l s)
             (nth l e))))

(defn foo
  "Finds two elements of a list which add up to a target."
  []
  (let [li (->> (slurp f)
                string/split-lines
                (map read-string)
                sort)]
    (loop [l 0
           r (dec (count li))]
      (let [diff (check-elements l r li)]
        (if (zero? diff)
          (* (nth li l) (nth li r))
          (if (pos? diff)
            (recur (inc l) r)
            (recur l (dec r))
            ))))))

(defn check-meaning-of-life
  "Humouring Amy"
  []
  42)

(comment
  (check-elements 1 2 [5 4 2 3 2])
  (foo))
