(ns lewis-core.core
  (:require
    [clojure.java.io :as io]
    [clojure.tools.logging :as log]
    [me.raynes.fs :as fs]
    ))

(use '[clojure.string :only (join split)])

(def not-nil? (complement nil?))

(defmulti #^{:private true} install :type :default :oops)

(defmethod #^{:private true} install :file [params dest-dir]
  "Install from file"
  (log/debug "installing pipeline from file")
  (def from (params :path))
  (assert (not-nil? from))
  (log/trace "copy" from "->" dest-dir)
  (fs/copy-dir from dest-dir)
  (println "Reading config...")
  (read-config dest-dir)
  )

(defn- read-config
  [path]
  "Read the config file"
  (path)
  )
(defmethod #^{:private true} install :git [params dest-dir]
  (log/debug "installing pipeline from git repo")
  )

(defn install-pipeline [app-config pipeline-config]
  "Installs the pipeline to the home dir
   The pipeline config can be {:git git-url} or {:file file-path}
   If git - it'll `git clone` the repo to ${home-dir}/pipelines/${name}
  "
  (log/trace "installing:" pipeline-config)
  (assert (not-nil? (app-config :home-dir)))
  (let [ pipeline-dir (join "/" [(app-config :home-dir) "pipelines"])
         dest-dir (join "/" [pipeline-dir (pipeline-config :name) "pipeline"]) ]
    (fs/mkdirs pipeline-dir)
    (install pipeline-config dest-dir)
    {:result {:installation-path dest-dir}}))
