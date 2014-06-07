(ns lewis-core.core
  (:require [clojure.java.io :as io] )
  )

(use '[clojure.string :only (join split)])

 
; Populate the file on the command line: 
;   echo "Hello Resources!" > resources/hello.txt
(def data-file (io/file
                 (io/resource
                   "a.json" )))
(defn -main []
  (println (slurp data-file)) )


(defn run-flow
  "Run a dev flow"
  [path flow-name]
  (join " " [path flow-name])
  )
