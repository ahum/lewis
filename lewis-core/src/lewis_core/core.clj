(ns lewis-core.core
  (:require
    [clojure.java.io :as io]
    [clojure.tools.logging :as log]
    [me.raynes.fs :as fs]
    [lewis-core.cfg :as cfg]
    ))

(use '[clojure.string :only (join split)])

(def not-nil? (complement nil?))

;(defmulti foo (fn [x] (first (map key x))) :default :unknown)

(defn- first-key
  "Return the first key in the map"
  [m & more]
  (first (map key m)))

(defmulti #^{:private true} install-src first-key)

(defmethod #{:private true} install-src :file
   "Install src from file"
   [m dest-dir]
   (def src-path (m :file))
   (assert (not-nil? src-path))
   (log/trace "copy" src-path "->" dest-dir)
   (fs/copy-dir src-path dest-dir))

(defmethod #{:private true} install-src :git [m dir] (println "TODO"))

(defmulti #^{:private true} install :type :default :oops)

(defmethod #^{:private true} install :file [params dest-dir]
  "Install from file"
  (log/debug "installing pipeline from file")
  (def from (params :path))
  (assert (not-nil? from))
  (log/trace "copy" from "->" dest-dir)
  (fs/copy-dir from dest-dir)
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
         name (pipeline-config :name)
         dest-dir (join "/" [pipeline-dir name "pipeline"])
         src-dest-dir (join "/" [pipeline-dir name "repo"])
         ]
    (fs/mkdirs pipeline-dir)
    (install pipeline-config dest-dir)
    ;TODO...
    (println "Reading config...")
    (def config cfg/read-cfg dest-dir)
    (install-src (config :src) src-dest-dir)
    {:result {:installation-path dest-dir}}))
