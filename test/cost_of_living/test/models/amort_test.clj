(ns cost-of-living.test.models.amort_test
(:use clojure.test)
(:require [cost-of-living.models.amort :as amort]
			[cost-of-living.helpers.param_utils :as utils]))


(def expected-amort-schedule
[
 {:month 1 :payment_cents 8475 :principal_cents 8215 :interest_cents 260 :total_interest_cents 260 :balance_cents 91785}
 {:month 2 :payment_cents 8475 :principal_cents 8236 :interest_cents 239 :total_interest_cents 499 :balance_cents 83549}
 {:month 3 :payment_cents 8475 :principal_cents 8257 :interest_cents 218 :total_interest_cents 717 :balance_cents 75292}
 {:month 4 :payment_cents 8475 :principal_cents 8279 :interest_cents 196 :total_interest_cents 913 :balance_cents 67013}
 {:month 5 :payment_cents 8475 :principal_cents 8301 :interest_cents 175 :total_interest_cents 1088 :balance_cents 58713}
 {:month 6 :payment_cents 8475 :principal_cents 8322 :interest_cents 153 :total_interest_cents 1241 :balance_cents 50391}
 {:month 7 :payment_cents 8475 :principal_cents 8344 :interest_cents 131 :total_interest_cents 1372 :balance_cents 42047}
 {:month 8 :payment_cents 8475 :principal_cents 8366 :interest_cents 109 :total_interest_cents 1481 :balance_cents 33681}
 {:month 9 :payment_cents 8475 :principal_cents 8387 :interest_cents 88 :total_interest_cents 1569 :balance_cents 25294}
 {:month 10 :payment_cents 8475 :principal_cents 8409 :interest_cents 66 :total_interest_cents 1635 :balance_cents 16885}
 {:month 11 :payment_cents 8475 :principal_cents 8431 :interest_cents 44 :total_interest_cents 1679 :balance_cents 8454}
 {:month 12 :payment_cents 8475 :principal_cents 8453 :interest_cents 22 :total_interest_cents 1701 :balance_cents 1} ;; rounding
 ]
)

(deftest test-amort-schedule
(is (= (map :total_interest_cents expected-amort-schedule) (map :total_interest_cents (amort/amort-schedule 1000 1 3.125))))
(is (<= 1 (- (utils/in-cents 1000.0) (reduce + (map :principal_cents (amort/amort-schedule 1000 1 3.125)))))))

(deftest test-calculate-payment
(is (= 8475 (amort/calculate-payment 1000 12 3.125)))
)
