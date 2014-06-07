(defproject lewis "0.1.0-SNAPSHOT"
  :description "A build pipeline manager"
  :url "http://github.com/ahum/lewis"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]]
  :plugins [[lein-sub "0.3.0"]]
  :sub
    ["lewis-core"
     "lewis-web"]
  )
