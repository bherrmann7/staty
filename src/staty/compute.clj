(ns staty.compute
  (:require [hiccup.core :as h]))

(defn compute-stats [schema table password]
  (h/html
    [:h1 "Stats for " schema " " table]))
