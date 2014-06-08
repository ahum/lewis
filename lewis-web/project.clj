(defproject lewis-web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [
                [org.clojure/clojure "1.6.0"]
                [org.clojure/tools.logging "0.3.0"]
                [compojure "1.1.6"]
                [ring/ring-json "0.3.1"]
                [lewis-core "0.1.0-SNAPSHOT"]
                ;; LOGGING DEPS
                [org.slf4j/slf4j-log4j12 "1.7.7"]
                [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler lewis-web.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
