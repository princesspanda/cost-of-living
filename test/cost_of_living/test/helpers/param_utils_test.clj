(ns cost-of-living.test.helpers.param_utils_test
(:use clojure.test
			cost-of-living.helpers.param_utils))

(deftest test-parse-int
				 (is (= 0 (parse-int "0")))
				 (is (= 123 (parse-int "123abc")))
				 (is (= 123 (parse-int "foo123abc")))
				 (is (= 123 (parse-int "foo123abc456")))
				 (is (= 0 (parse-int ""))))

(deftest test-parse-double
(is (= 0.0 (parse-double "0.0")))
(is (= 3.125 (parse-double "abc3.125p")))
(is (= 0.50 (parse-double "abc0.50xyz 123")))

)