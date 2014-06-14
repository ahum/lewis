(ns lewis-core.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [clojure.java.io :as io]
            [me.raynes.fs :as fs]
            [lewis-core.core :as lc]
            [lewis-core.file-helper :as fh]
            ))

(use-fixtures :once fh/clean-tmp)

(deftest install-pipeline-test
  (testing "A pipeline is installed from file"
    (let
      [ app-config {:home-dir (fh/tmp-path "core-test-one")}
        params {:type :file, :path (fh/mock-pipeline "core-test-one"), :name "my-app"}
        result (lc/install-pipeline app-config params)
        dest-dir (fh/join (app-config :home-dir) "pipelines/my-app/pipeline")
        expected { :result {:installation-path dest-dir}}]
      (is (= expected result))
      (is (fs/directory? dest-dir))
      (is (fs/exists? (fh/join dest-dir "pipeline.yml")))
      )))
