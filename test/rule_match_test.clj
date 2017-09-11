(ns rule-match_test
  (:require [clojure.test :refer :all]
            [logical-interpreter :refer :all]))

(def allRules ["hijo(X, Y) :- varon(X), padre(Y, X)", "hija(X, Y) :- mujer(X), padre(Y, X))" ])

(deftest is_rule_match_ok_test
  (testing "varon(juan) should be true"

    (is (= (getRuleMatch allRules "varon(juan)") nil))) 

  (testing "hijo(X, Y) :- varon(X), padre(Y, X)"
   (is (= (getRuleMatch allRules "hijo(Alejandrio, roberto)") "hijo(X, Y) :- varon(X), padre(Y, X)"))))

(deftest is_rule_test
  (testing "hija(X, Y) :- mujer(X), padre(Y, X))"
    (is (= (getRuleMatch allRules "hija(juan)") "hija(X, Y) :- mujer(X), padre(Y, X))"))) 

  (testing "que onda? should be nil"
    (is (= (getRuleMatch allRules "que onda()") nil))) )
