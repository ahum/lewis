(ns lewis-core.core
  (:require [clojure.java.io :as io]))

(use '[clojure.string :only (join split)])

(defn ping
  []
  "ping"
  "pong")
