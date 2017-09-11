(ns drink-database-test
  (:require [clojure.test :refer :all]
            [logical-interpreter :refer :all]))

(def drink-database "
	comprar(coca, sprite).
	guardar(sprite).
	comprar(sprite, fanta).
	comprar(agua, sprite).
	vender(agua).
	vender(sprite).
	administrar(X, Y, Z) :- guardar(X), comprar(Z, Y).
	cambios(X, Y, Z) :- vender(Y), comprar(Z, X).
")

(deftest drink-database-fact-test
  (testing "comprar(coca, sprite) should be true"
    (is (= (evaluate-query drink-database "comprar(coca, sprite)")
           true)))
  (testing "comprar(sprite, coca) should be false"
    (is (= (evaluate-query drink-database "comprar(sprite, coca)")
           false)))
  (testing "comprar(agua, sprite) should be true"
    (is (= (evaluate-query drink-database "comprar(agua, sprite)")
           true)))  
  (testing "vender(sprite, coca) should be false"
    (is (= (evaluate-query drink-database "vender(sprite, coca)")
           false)))
  (testing "vender(agua) should be false"
  (is (= (evaluate-query drink-database "vender(agua)")
           true))))
           
(deftest drink-database-rule-test
  (testing "administrar(sprite, sprite, agua) should be true"
    (is (= (evaluate-query drink-database "administrar(sprite, sprite, agua)")
           true)))
  (testing "cambios(fanta, agua, sprite) should be true"
    (is (= (evaluate-query drink-database "cambios(fanta, agua, sprite)")
           true)))
  (testing "administrar(sprite, fanta, agua) should be true"
    (is (= (evaluate-query drink-database "administrar(sprite, fanta, agua)")
           false)))
  (testing "subtract(two, one, one) should be false"
    (is (= (evaluate-query drink-database "subtract(two, one, one)")
           false))))
