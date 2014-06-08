(ns lewis-core.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [clojure.java.io :as io]
            [me.raynes.fs :as fs]
            [lewis-core.core :as lc]))

(defn join [& bits] (str/join "/" bits))

(def root-path
   (let [p (io/as-relative-path ".") f (io/file p)]
     (.. f getAbsolutePath)))

(def tmp-dir (join root-path ".tmp"))

(println "root path is" root-path)
(println ".tmp path is" tmp-dir)

(defn tmp-path
  [path]
  "A tmp path for test pipeline installations"
  (join tmp-dir path))

(defn mock-pipeline
  [path]
  "The path to the mocks folder"
  (join root-path "test-resources" "mocks" path "pipeline"))

(defn clean-tmp [f] "Clean the .tmp folder"
  (println "cleaning" tmp-dir)
  (fs/delete-dir tmp-dir)
  (f))

(use-fixtures :once clean-tmp)

(deftest install-pipeline-test
  (testing "A pipeline is installed from file"
    (let
      [ app-config {:home-dir (tmp-path "core-test-one")}
        params {:type :file, :path (mock-pipeline "core-test-one"), :name "my-app"}
        result (lc/install-pipeline app-config params)
        expected {
          :result {
            :installation-path (join (app-config :home-dir) "pipelines/my-app/pipeline")
            }
        }]
      (is (= expected result))
      (is (fs/directory? dest-dir))
      (is (fs/exists? (join dest-dir "pipeline.cson")))
      )))
