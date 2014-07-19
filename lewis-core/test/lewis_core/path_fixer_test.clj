(ns lewis-core.path-fixer-test
  (:require 
    [lewis-core.path-fixer :as pf]
    [lewis-core.file-helper :as fh]
    [clojure.java.io :as io]
    [clojure.test :refer :all]))

(def root "./some-dir/")

(defn assert-expand
  [src rel-path expected-path]
  (testing (str "from " src " with path " rel-path " gives " expected-path))
  (let 
    [ src-file (io/file (str root src))
      expected-file (io/file (str root expected-path))
      full-expected-path (.. expected-file getCanonicalPath)
      result (pf/expand-path src-file rel-path) ]
      (println "result" result)
      (println "expected" full-expected-path)
      (is (= result full-expected-path))))

(deftest ept 
  (assert-expand "a/one.txt" ".." ".")
  (assert-expand "a/one.txt" "../b" "b")
  (assert-expand "a/c" "../b" "b")
  (assert-expand "a/c/d/e" "../b" "a/c/b"))

