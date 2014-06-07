(ns lewis-core.sh
    (:require [clojure.java.shell :as csh]))

;;; TODO: Look at https://github.com/Raynes/conch
;;; for piping shell output back

(defn run [script root]
  "Run an external shell script.
   Return the output only, throw an exception if exit code != 0"
  (let [ result (csh/sh script :dir root) ]
    (assert (= 0 (result :exit)))
    (result :out)))
