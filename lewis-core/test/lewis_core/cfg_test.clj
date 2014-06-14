(ns lewis-core.cfg-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [clojure.java.io :as io]
            [lewis-core.cfg :as lcfg]
            [lewis-core.file-helper :as fh]))

(defn cfg-dir [p]
  (fh/join fh/root-path "test-resources" "cfg_test" p))

(deftest get-suffix-test
  (testing "get suffix works"
    (is (= (lcfg/get-suffix "a/b/c.yml") :yml))
    (is (= (lcfg/get-suffix "a/b/c.yaml") :yml))
  ))

(deftest get-pipeline-path-and-suffix-test
  (testing "the path and suffix to the pipeline are returned"
    (let
      [ result (lcfg/get-pipeline-path-and-suffix (cfg-dir "one"))
        expected [(cfg-dir "one/pipeline.yml") :yml]]
      (is (= expected result)))))


(deftest read-cfg-test
  (testing "Reading the config works.."
    (let
      [result (lcfg/read-cfg (cfg-dir "one"))
      expected {:name "test-config" :description "some description"}]
      (is (= expected result)))))
