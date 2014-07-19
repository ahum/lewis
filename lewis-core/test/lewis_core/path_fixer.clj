(ns lewis-core.path-fixer
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [me.raynes.fs :as fs]))

(defn expand-path [file rel-path]
  "Turn a path ie '..' to a full path relative to some file"
  (let 
    [ parent (.getParentFile (.getAbsoluteFile file))
      parent-path (.getAbsolutePath parent)
      new-path (str parent-path "/" rel-path)]
      (.getCanonicalPath (io/file new-path))))