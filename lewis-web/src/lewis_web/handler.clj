(ns lewis-web.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.string :as str]
            [lewis-core.core :as lc]))

(defroutes app-routes
  (GET "/" [] "lewis web")
  (GET "/pipelines" [] "get pipelines")
  (GET "/pipelines/:name" [name] (str/join " " ["get pipelines" name (lc/ping)]))
  (DELETE "/pipelines/:name" [name] (str/join " " ["delete pipeline" name]))
  (GET "/pipelines/:name/update" [name] "update pipeline")
  (POST "/pipelines" [] "install pipeline")
  (GET "/pipelines/:name/:flow/trigger" [name flow] (str/join " " ["trigger flow" flow "for pipeline " name]))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
