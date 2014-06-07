(ns lewis-core.project-test
  (:require [clojure.java.io :as io] )
  (:require [clojure.test :refer :all]
            [lewis-core.project :as lcp]
            [clojure.java.io :as io]))


(defn assert-task-to-command
  "Run a test base on a map with :cmd-map and :tasks"
  [m]
    (println "[assert-task-to-command]" m)
    (testing "?"
      (let [ result (lcp/tasks-to-commands (m :cmd-map) (m :tasks)) ]
      (is (= result (m :expected))))))

(deftest run-all-tasks-to-commands
  (let [ one {:cmd-map {"a" "a-cmd"} :tasks ["a"] :expected ["a-cmd"]}
    two { :cmd-map {"a" "a-cmd", "b", "b-cmd"}
      :tasks ["a" "b"]
      :expected ["a-cmd", "b-cmd"]}]
   (assert-task-to-command one)
   (assert-task-to-command two)))

(deftest tasks-to-commands
  (testing "convert a task name to a command"
    (let
      [ result (lcp/tasks-to-commands {"a" "a cmd"} ["a"]) ]
        (prn "result: " result)
        (is (= result ["a cmd"]))
      )
    )
  )

(deftest read-json
  (testing "Can read json"
    (println (io/resource "one/a.json"))
    (println (io/file (io/resource "one/a.json")))
    (let [
        r (io/resource "one/def.json")
        f (io/file r)
        s (slurp f)
        m (lcp/read-json f)
        ]
      (println s m)
      (is (= (m )))
      )
    ))
