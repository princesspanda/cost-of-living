(ns cost-of-living.test.models.taxes
(:require [cost-of-living.models.taxes :as taxes])
(:use clojure.test
			))

(deftest test-calculate-brackets
(is (= [[0.10 9075] [0.15 27825] [0.25 33100]] (taxes/calculate-brackets 70000 :single)))
(is (= [[0.10 18150] [0.15 55650] [0.25 75050] [0.28 51150]] (taxes/calculate-brackets 200000 :jointly)))
(is (= [[0.10 5000]] (taxes/calculate-brackets 5000 :single)))
(is (= [[0.10 18150] [0.15 55650] [0.25 75050] [0.28 78000] [0.33 178250] [0.35 52500] [0.396 42400]] (taxes/calculate-brackets 500000 :jointly)))
)

(deftest test-average-tax-rate
(is (= 0.19 (taxes/average-tax-rate 70000 :single)))
(is (= 0.10 (taxes/average-tax-rate 5000 :single)))
)