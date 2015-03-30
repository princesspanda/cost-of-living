(ns cost-of-living.test.helpers.schedule_test
(:use clojure.test
			cost-of-living.helpers.schedule))

(deftest test-yearly-interest-cents
(let [fake-schedule (reduce #(conj %1 {:month %2 :interest_cents %2 }) [] (range 1 37))]
(is (= (reduce + (range 1 13)) (yearly-interest-cents fake-schedule 1)))
(is (= (reduce + (range 13 25)) (yearly-interest-cents fake-schedule 2)))

(is (= "Year cannot be < 1" (yearly-interest-cents fake-schedule 1)))
(is (= "Year requested exceeds length of term" (yearly-interest-cents fake-schedule 42)))
)
)