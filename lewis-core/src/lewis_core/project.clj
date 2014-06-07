(ns lewis-core.project
  (:require
    [clojure.java.io :as io]
    [clojure.data.json :as json]
    [clojure.string :as str]
    [lewis-core.sh :as lsh]
  ))

(defn read-json
  "Read json from file"
  [file]
  (assert (not (nil? file)))
  (json/read-str (slurp file)))

(defn read-json-from-path
  "Read from string path"
  [path]
  (let [r (io/resource path) ]
    (assert (not (nil? r)))
    (read-json (io/file r))))

(defn script-root
  "Get the full path to the directory for a path"
  [path & remainder]
  (let
    [ r (io/resource path)
      f (io/file r)
      abs-path (.. f getParentFile getAbsolutePath)
      all (concat [abs-path] remainder) ]
    (clojure.string/join "/" all)))


(defn tasks-to-commands
  "For a def and a flow get the commands for the named task"
  [commands tasks]
  (map (fn [t] (commands t))  (vec tasks))
  )

;; Usage:
; (lcp/run-flow "one/def.json" "dev")
(defn run-flow
  "Run a given flow"
  [path flow]
  (let
    [ project-def (read-json-from-path path)
      f (project-def "flows")
      flow-def (f flow)
      root (script-root path (project-def "path"))
      tasks (flow-def "tasks")
      cmds-to-run (tasks-to-commands (project-def "cmds") tasks)]
    (assert (not (nil? cmds-to-run)))
    (let
      [ command-output (map (fn [c] (lsh/run c root)) cmds-to-run) ]
      (println (str/join "--\n" command-output)))))
