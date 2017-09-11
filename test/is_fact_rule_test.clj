(ns is_fact_rule_test
  (:require [clojure.test :refer :all]
            [logical-interpreter :refer :all]))

(deftest is_fact_rule_test
  (testing "varon(juan) should be true"
    (is (= (isFact "varon(juan)") true))) 
  (testing "hijo(X, Y) :- varon(X), padre(Y, X) should be false"
    (is (= (isFact "hijo(X, Y) :- varon(X), padre(Y, X)") false))))
(deftest is_rule_test
  (testing "varon(juan) should be false"
    (is (= (isRule "varon(juan)") false))) 
  (testing "hijo(X, Y) :- varon(X), padre(Y, X) should be true"
    (is (= (isRule "hijo(X, Y) :- varon(X), padre(Y, X)") true))))

(deftest nor_test
  (testing "varon(juan) should be false"
    (is (= (notFactOrRule "varon(juan)") false))) 
  (testing "hijo(X, Y) :- varon(X), padre(Y, X) should be false"
    (is (= (notFactOrRule "hijo(X, Y) :- varon(X), padre(Y, X)") false)))
  (testing "varon should be true"
    (is (= (notFactOrRule "varon") true)))
  (testing "hijo( should be true"
    (is (= (notFactOrRule "hijo(") true))))
