(ns lewis-core.cfg-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [clojure.java.io :as io]
            [lewis-core.cfg :as lcfg]))

  

(deftest get-suffix-test
  (testing "get suffix works"
    (is (= (lcfg/get-suffix "a/b/c.yml") :yml))
    (is (= (lcfg/get-suffix "a/b/c.yaml") :yml))
  ))
