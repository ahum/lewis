(defproject lewis-web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"
                  lewis-core "0.1.0-SNAPSHOT"]]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler lewis-web.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
