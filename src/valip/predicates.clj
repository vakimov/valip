(ns valip.predicates
  "Predicates useful for validating input strings, such as ones from a HTML
  form."
  (:import java.text.NumberFormat)
  (:require [clojure.string :as string]))

(defn present?
  "Returns false if x is nil or blank, true otherwise."
  [x]
  (not (string/blank? x)))

(defn matches
  "Returns a predicate that returns true if the supplied regular expression
  matches its argument."
  [re]
  (fn [s] (boolean (re-matches re s))))

(defn- parse-number [x]
  (if (string? x)
    (. (NumberFormat/getInstance) (parse x))
    x))

(defn gt
  "Creates a predicate function for checking if a value is numerically greater
  than the specified number."
  [n]
  (fn [x] (> (parse-number x) n)))

(defn lt
  "Creates a predicate function for checking if a value is numerically less
  than the specified number."
  [n]
  (fn [x] (< (parse-number x) n)))

(defn gte
  "Creates a predicate function for checking if a value is numerically greater
  than or equal to the specified number."
  [n]
  (fn [x] (>= (parse-number x) n)))

(defn lte
  "Creates a predicate function for checking if a value is numerically less
  than or equal to the specified number."
  [n]
  (fn [x] (<= (parse-number x) n)))

(def ^{:doc "Alias for gt"} over gt)

(def ^{:doc "Alias for lt"} under lt)

(defn between
  "Creates a predicate function for checking whether a number is between two
  values (inclusive)."
  [min max]
  (fn [x]
    (let [x (parse-number x)]
      (and (>= x min) (<= x max)))))