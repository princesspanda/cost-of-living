(ns cost-of-living.test.models.deductions
(:use clojure.test
			cost-of-living.models.deductions))

(deftest test-yearly-deductions
  (is (= (repeat 3 15300) (yearly-deductions 10000 5000 (repeat 3 100) (repeat 3 200))))
  (is (= [] (yearly-deductions 10000 5000 (repeat 3 100) [])))
)