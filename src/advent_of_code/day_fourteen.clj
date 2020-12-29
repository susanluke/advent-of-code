(ns advent-of-code.day-fourteen
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def f       "day14-input.txt")
(def input   (-> f io/resource slurp))
(def mask-re #"mask = ([10X]+)")
(def mem-re  #"mem\[([0-9]+)\] = ([0-9]+)")

(defn s->mask
  "version 1, interprets Xs to be ignored"
  [s x-replacement]
  (read-string (str "2r" (string/replace s #"X" x-replacement))))

(defn append-char-to-all-elements [l c]
  (map #(str % c) l))

(defn s->masks
  "version 2, interprets X as either 1 or 0"
  [s]
  (->> s
       (reduce (fn [l c]
                 (if (or (= \0 c) (= \1 c))
                   (append-char-to-all-elements l c)
                   (concat (append-char-to-all-elements l 0)
                           (append-char-to-all-elements l 1))))
               [""])
       (map #(read-string (str "2r" %)))))

(defn s->instruction [v s]
  (if-let [[_ mask] (re-matches mask-re s)]
    [:mask (if (= :v1 v)
             {:and-mask (s->mask mask "1")
              :or-mask  (s->mask mask "0")}
             (s->masks mask))]
    (if-let [[_ mem-loc val] (re-matches mem-re s)]
      [:mem (read-string mem-loc) (read-string val)])))

(defn parse-string [v s]
  (->> (string/split-lines s)
       (map (partial s->instruction v))))

(defn apply-mask [{:keys [and-mask or-mask]} v]
  (-> v (bit-and and-mask) (bit-or or-mask)))

(defn perform-instruction-v1 [{:keys [mask] :as state} [cmd :as instruction]]
  (case cmd
    :mask (assoc state :mask (second instruction))
    :mem  (let [[l v] (rest instruction)]
            (assoc-in state [:memory l] (apply-mask mask v)))))

(defn perform-instruction-v2 [{:keys [mask] :as state} [cmd :as instruction]]
  (println "intermediate state:" state)
  (case cmd
    :mask (assoc state :mask (second instruction))
    :mem (let [[l v] (rest instruction)]
           (reduce #(assoc-in %1 [:memory (bit-or %2 l)] v)
                   state
                   mask))))

(defn perform-instructions [v instructions]
  (println (reduce (if (= :v1 v)
                     perform-instruction-v1
                     perform-instruction-v2)
                   {}
                   instructions))
  (->> instructions
       (reduce (if (= :v1 v)
                 perform-instruction-v1
                 perform-instruction-v2)
               {})
       :memory
       vals
       (apply +)))

(defn get-day14-answer-pt1 []
  (perform-instructions :v1 (parse-string :v1 input)))

;; 1756854330635 is too low
(defn get-day14-answer-pt2 []
  (perform-instructions :v2 (parse-string :v2 input)))
