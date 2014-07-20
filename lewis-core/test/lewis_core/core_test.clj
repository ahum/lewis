(ns lewis-core.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [clojure.java.io :as io]
            [me.raynes.fs :as fs]
            [lewis-core.core :as lc]
            [lewis-core.file-helper :as fh]
            ))

(defn prep 
    []
    "Prepare the .tmp folder for testing"
    (fh/clean-tmp)
    (fh/add-mocks-to-tmp)
    (fh/prep-paths))

(use-fixtures :once prep)
; how to run a single test with fixtures?
(deftest install-pipeline-test
  (testing "A pipeline is installed from file"
    (let
      [ 
        home-dir (fh/tmp-path "core-test-one")
        app-config {:home-dir home-dir}
        params { :type :file, :path (fh/mock-pipeline "core-test-one"), :name "my-app"}
        result (lc/install-pipeline app-config params)
        install-dir (fh/join home-dir "pipelines/my-app")
        pipe-dir (fh/join install-dir "pipeline")
        expected { :result {:installation-path pipe-dir}}]
      (is (= expected result))
      (is (fs/directory? pipe-dir))
      (is (fs/exists? (fh/join pipe-dir "pipeline.yml")))
      (is (fs/exists? (fh/join install-dir "repo")))
      )))
