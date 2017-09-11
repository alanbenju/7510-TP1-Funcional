(ns exist-test
  (:require [clojure.test :refer :all]
            [logical-interpreter :refer :all]))

(def parent-database "
	varon(juan).
	varon(pepe).
	varon(hector).
	varon(roberto).
	varon(alejandro).
	mujer(maria).
	mujer(cecilia).
	padre(juan, pepe).
	padre(juan, pepa).
	padre(hector, maria).
	padre(roberto, alejandro).
	padre(roberto, cecilia).
	hijo(X, Y) :- varon(X), padre(Y, X).
	hija(X, Y) :- mujer(X), padre(Y, X).
")

(deftest exist-in-list-test
  (testing "varon(juan) should be true"
    (is (= (exist (getList parent-database) "varon(juan)")
           true)))
  (testing "varon(maria) should be false"
    (is (= (exist (getList parent-database) "varon(maria)")
           false)))
  (testing "mujer(cecilia) should be true"
    (is (= (exist (getList parent-database) "mujer(cecilia)")
           true)))
  (testing "padre(juan, pepe) should be true"
    (is (= (exist (getList parent-database) "padre(juan, pepe)")
           true)))
  (testing "padre(mario, pepe) should be false"
    (is (= (exist (getList parent-database) "padre(mario, pepe)")
           false))))