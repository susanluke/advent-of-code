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

(defn s->instruction [v s]
  (if-let [[_ mask] (re-matches mask-re s)]
    [:mask (if (= :v1 v)
             {:and-mask (s->mask mask "1")
              :or-mask  (s->mask mask "0")}
             mask)]
    (if-let [[_ mem-loc val] (re-matches mem-re s)]
      [:mem (read-string mem-loc) (read-string val)])))

(defn parse-string [v s]
  (->> (string/split-lines s)
       (map (partial s->instruction v))))

(defn apply-mask [{:keys [and-mask or-mask]} v]
  (-> v (bit-and and-mask) (bit-or or-mask)))

(defn append-binary-digit-to-all-elements [l b]
  (map #(-> %
            (bit-shift-left 1)
            ((fn [n] (if (= b 1) (bit-set n 0) n))))
       l))

(defn perform-instruction-v1 [{:keys [mask] :as state} [cmd :as instruction]]
  (case cmd
    :mask (assoc state :mask (second instruction))
    :mem  (let [[l v] (rest instruction)]
            (assoc-in state [:memory l] (apply-mask mask v)))))

(defn bit-at [x n]
  (if (bit-test x n) 1 0))

(defn char->bit [c]
  (read-string (str c)))

(defn perform-instruction-v2 [{:keys [mask] :as state} [cmd :as instruction]]
  (case cmd
    :mask (assoc state :mask (second instruction))
    :mem  (let [[mem-loc v] (rest instruction)
                mem-locs    (reduce #(if (or (= \0 %2) (= \1 %2))
                                       {:mem-locs (append-binary-digit-to-all-elements (:mem-locs %)
                                                                                       ;;mask is 36 chars long
                                                                                       (bit-or (bit-at mem-loc (- 35 (:i %))) (char->bit %2)))
                                        :i        (inc (:i %))}
                                       {:mem-locs (concat
                                                   (append-binary-digit-to-all-elements (:mem-locs %) 0)
                                                   (append-binary-digit-to-all-elements (:mem-locs %) 1))
                                        :i        (inc (:i %))})
                                    {:mem-locs [0]
                                     :i        0}
                                    mask)]
            (reduce #(assoc-in %1 [:memory %2] v)
                    state
                    (:mem-locs mem-locs)))))

(defn perform-instructions [v instructions]
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

(defn get-day14-answer-pt2 []
  (perform-instructions :v2 (parse-string :v2 input)))
