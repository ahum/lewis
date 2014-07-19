(ns lewis-core.file-helper
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [me.raynes.fs :as fs]
            [clj-yaml.core :as yaml]
            [clojure.walk :as walk]
            [lewis-core.path-fixer :as pf]
            ))

(defn join [& bits] (str/join "/" bits))

(def root-path
   (let [p (io/as-relative-path ".") f (io/file p)]
     (.. f getAbsolutePath)))

(def tmp-dir (join root-path ".tmp"))
(def mocks-tmp (join tmp-dir "mocks"))

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

(defn add-mocks-to-tmp [] "copy mock over to tmp"
  (fs/copy-dir (join root-path "test-resources" "mocks") tmp-dir))

(defn process-yml [yml-path]
  "process the yml file "

  (defn visit-prop [p] 
    (if 
      (and (string? p) (.startsWith p "..")) 
        (let 
          [ out (pf/expand-path (io/file yml-path) p) ] 
          out)
        p))

  (let 
    [config (yaml/parse-string (slurp yml-path))
     processed (walk/postwalk visit-prop config)
     yml-str (yaml/generate-string processed)]
     (spit yml-path yml-str)))

(defn prep-paths [] "Turn relative paths into absolute paths"
  ;;;;;;;;;;;;;;; here.......
  ;(filter #(.endsWith (.getName %) ".yml") (file-seq (io/file ".")))
  (def only-yml (fn [f] (.endsWith (.getName f) ".yml")))
  (def yml-files (filter only-yml (file-seq (io/file mocks-tmp))))

  (defn fix-paths [yml-file]
    "fix the paths from relative to absolute in the yml-file"
    (println "fix paths >> " yml-file)
    (process-yml yml-file))

  (println "found yml-files: " (seq yml-files))
  ; now for each file - fix paths
  (doseq [f yml-files] (fix-paths f))
  )
