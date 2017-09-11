(ns prepare_dic-test
  (:require [clojure.test :refer :all]
            [logical-interpreter :refer :all]))


(def dic1 {:X "one", :Y  " zero", :Z  " one"})
(def dic2 {:X "alejandro", :Y " roberto"})

(deftest prepare_dic-test-subtract
  (testing " should be true"
    (is (= (prepareDic "subtract(X, Y, Z) :- add(Y, Z, X)" "subtract(one, zero, one)") dic1)))
  (testing " should be true"
    (is (= (prepareDic "hijo(X, Y) :- varon(X), padre(Y, X)" "hijo(alejandro, roberto)") dic2)))
) 