(ns lewis-core.file-helper
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [me.raynes.fs :as fs]))

(defn join [& bits] (str/join "/" bits))

(def root-path
   (let [p (io/as-relative-path ".") f (io/file p)]
     (.. f getAbsolutePath)))

(def tmp-dir (join root-path ".tmp"))

(defn tmp-path
  [path]
  "A tmp path for test pipeline installations"
  (join tmp-dir path))

(defn mock-pipeline
  [path]
  "The path to the mocks folder"
  (join root-path "test-resources" "mocks" path "pipeline"))

(defn clean-tmp [f] "Clean the .tmp folder"
  (println "cleaning" tmp-dir)
  (fs/delete-dir tmp-dir)
  (f))
