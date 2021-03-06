(ns cost-of-living.test.handler
  (:use clojure.test
        ring.mock.request
        cost-of-living.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "Hello World"))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404))))

	(testing "schedule route"
	 (let [response (app (request :get "/schedule", { :principal 1000
																									 :interest-rate 3.125
																									 :term-years 15
																									 :p1-income 40000
																									 :p2-income 60000
																									 :p1-401k 0
																									 :p2-401k 0}))]
	   (is (= (get-in response [:headers "Content-Type"])  "application/json; charset=utf-8"))
	 )))
