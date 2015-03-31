(ns cost-of-living.routes.home
  (:require [compojure.core :refer :all]
            [cost-of-living.views.layout :as layout]
						[cost-of-living.models.amort :as amort]
						[cost-of-living.models.deductions :as deductions]
						[cost-of-living.helpers.schedule :as schedule]
						[cost-of-living.helpers.param_utils :as utils])
)

(defn home []
  (layout/common [:h1 "Hello World!"]))

(defroutes home-routes
  (GET "/" [] (home))
	(GET "/schedule" [principal
										interest-rate]
			 (let [term-years 15
						 schedule (amort/amort-schedule (utils/parse-int principal)  term-years  (utils/parse-double interest-rate))
						 p1-income 100000
						 p2-income 100000
						 joint-pretax-income (+ p1-income p2-income)
						 p1-401k 18000
						 p2-401k 10000
						 mortgage-interest-deductions (reduce #(conj %1 (utils/in-dollars (schedule/yearly-interest-cents schedule %2))) [] (range 1 (inc term-years)))
						 property-tax-deductions (vec (repeat term-years 5000))
						 yearly-deductions-with-ownership (deductions/yearly-deductions p1-401k p2-401k mortgage-interest-deductions property-tax-deductions)
						 yearly-deductions-no-ownership (deductions/yearly-deductions p1-401k p2-401k (repeat term-years 0) (repeat term-years 0))]
			 {:body {
							 :schedule schedule
							 :mortgage-interest-deductions mortgage-interest-deductions
							 :property-tax-deductions  property-tax-deductions ;; naive simplifying assumptions for now -- revisit later
							 :joint-pretax-income joint-pretax-income
							 :yearly-deductions {
																	 :with-ownership yearly-deductions-with-ownership
																	 :no-ownership yearly-deductions-no-ownership
																	 }
							 :taxable-income {
																:with-ownership (map #(- joint-pretax-income %1) yearly-deductions-with-ownership)
																:no-ownership (map #(- joint-pretax-income %1) yearly-deductions-no-ownership)
																}
							 ;:millage-rate 24.0
							 ;:tax-assessed-value 230000
							 }}
			 )
			 ))
