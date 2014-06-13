(ns lewis-core.cfg
  (:require
    [clojure.java.io :as io]
    [clojure.string :as str]
    [clojure.tools.logging :as log]
    [me.raynes.fs :as fs]))

(require '[clj-yaml.core :as yaml])

(def type-map { :yml ["yml", "yaml"], :xml ["xml"]})

(defn matches-suffix
  [suffix]
  "Returns a function that checks if val seq contains suffix"
  (fn [kv] (some #{suffix} (val kv))))

(defn get-suffix [path]
  "Get file suffix"
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

(defn get-pipeline-path-and-suffix [dir-path]
  "Return the path to the pipeline file and its suffix"
  (let [
    dir (io/file dir-path)
    files (file-seq dir)
    pipeline (some #(.startsWith % "pipeline") files)]
    (assert (not (nil? pipeline)))
    [pipeline (get-suffix pipeline)]))

(defmulti #^{:private true} parse-config :suffix :default :suffix-not-supported)

(defmethod #^{:private true} parse-config :yml [path]
  "Install from file"
  (log/debug "parsing yml from" path)
  (yaml/parse-string (slurp path)))

(defn read-cfg [path]
  "Read the config"
  (let [[path suffix] get-pipeline-path-and-suffix]
    (parse-config {:suffix suffix :path path})))
