(ns lewis-web.handler
(:use compojure.core)
(:require [compojure.handler :as handler]
          [compojure.route :as route]
          [clojure.string :as str]
          [lewis-core.core :as lc]
          [lewis-web.config :as cfg]
          [clojure.tools.logging :as log]
          ))

(use '[ring.middleware.json :only [wrap-json-response wrap-json-body]]
     '[ring.util.response :only [response]])


(def app-cfg (cfg/load-config))
(log/info "App booting up" app-cfg)
(log/debug ">> App booting up" app-cfg)

(defroutes app-routes
  (GET "/" [] "lewis web")
  (GET "/pipelines" [] (response [{:foo "bar"} {:baz "bing"}]))
  (GET "/pipelines/:name" [name] (str/join " " ["get pipelines" name]))
  (DELETE "/pipelines/:name" [name] (str/join " " ["delete pipeline" name]))
  (GET "/pipelines/:name/update" [name] "update pipeline")
  (POST "/pipelines" request
    (let
      [
        git (get-in request [:body "git"])
        result (lc/install-pipeline app-cfg {:git git})]
      (response {:result result})))
  (GET "/pipelines/:name/:flow/trigger" [name flow] (str/join " " ["trigger flow" flow "for pipeline " name]))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  ( ->
  (handler/site app-routes)
  (wrap-json-body)
  (wrap-json-response)))
