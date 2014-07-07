(ns lewis-core.cfg
  ^{:doc "Config loading/parsing"}
  (:require
    [clojure.java.io :as io]
    [clojure.string :as str]
    [clojure.tools.logging :as log]
    [me.raynes.fs :as fs]))

(require '[clj-yaml.core :as yaml])

(def type-map {
  :yml ["yml", "yaml"],
  :xml ["xml"]})

(defn- matches-suffix
  [suffix]
  "Returns a function that checks if val seq contains suffix"
  (fn [kv] (some #{suffix} (val kv))))

(defn get-suffix [path]
  "Get file suffix"
  (println "get-suffix" path)
  (def basename (fs/base-name path))
  (if (= (.indexOf basename ".") -1)
    :no-suffix
    (let [
      [name suffix] (str/split basename #"\.")
      entries (filter (matches-suffix suffix) type-map)
      entry (first entries)]
      (if (nil? entry)
        :unknown-suffix
        (key entry)))))

(defn is-pipeline [f]
  (log/debug "is pipeline? " f)
  (def file-name (.getName f))
  (log/trace "is pipeline? " file-name)
  (.startsWith file-name "pipeline"))

(defn get-pipeline-path-and-suffix [dir-path]
  "Return the path to the pipeline file and its suffix"
  (let [
    dir (io/file dir-path)
    files (.listFiles dir)
    pipeline (some #(if(is-pipeline %) %) files)]
    (println "files -> " files)
    (log/debug "found pipeline" pipeline)
    (assert (not (nil? pipeline)))
    [(.getAbsolutePath pipeline) (get-suffix pipeline)]))

(defmulti #^{:private true} parse-config :suffix :default :suffix-not-supported)

(defmethod #^{:private true} parse-config :no-suffix [obj]
  (log/warn "no suffix!")
  {}
  )

(defmethod #^{:private true} parse-config :yml [obj]
  "Install from file"
  (log/debug "parsing yml from" (obj :path))
  (yaml/parse-string (slurp (obj :path))))

(defn read-cfg
  "Read the config"
  [path]
  (log/debug "read-cfg" path)
  (let [[path suffix] (get-pipeline-path-and-suffix path)]
    (println "path and suffix" path suffix)
    (parse-config {:suffix suffix :path path})))
