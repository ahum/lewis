(ns rescue.core
 (:require [clojure.java.io :as io] ))
  (def data-file (io/file
                  (io/resource
                   "test/one/a.json" )))
  (defn -main []
   (println (slurp data-file)) )

