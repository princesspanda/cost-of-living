(ns cost-of-living.routes.home
  (:require [compojure.core :refer :all]
            [cost-of-living.views.layout :as layout]
						[cost-of-living.models.amort :as amort]
						[cost-of-living.models.deductions :as deductions]
						[cost-of-living.models.taxes :as taxes]
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
						 p1-income 140000
						 p2-income 130000
						 joint-pretax-income (+ p1-income p2-income)
						 p1-401k 10000
						 p2-401k 0
						 standard-deduction (vec (repeat term-years 13600))
						 mortgage-interest-deductions (reduce #(conj %1 (utils/in-dollars (schedule/yearly-interest-cents schedule %2))) [] (range 1 (inc term-years)))
						 yearly-principal-payments (reduce #(conj %1 (utils/in-dollars (schedule/yearly-principal-cents schedule %2))) [] (range 1 (inc term-years)))
						 yearly-rent-payments (repeat term-years (* 12 (utils/in-dollars (:payment_cents (first schedule)))))
						 property-tax-deductions (vec (repeat term-years 5000))
						 yearly-deductions-with-ownership (deductions/yearly-deductions p1-401k p2-401k mortgage-interest-deductions property-tax-deductions)
						 yearly-deductions-no-ownership (deductions/yearly-deductions p1-401k p2-401k standard-deduction)
						 taxable-income-with-ownership (map #(- joint-pretax-income %1) yearly-deductions-with-ownership)
						 taxable-income-no-ownership (map #(- joint-pretax-income %1) yearly-deductions-no-ownership)
						 basic-outputs {
														:schedule schedule
														:mortgage-interest-deductions mortgage-interest-deductions
														:property-tax-deductions  property-tax-deductions ;; naive simplifying assumptions for now -- revisit later
														:joint-pretax-income joint-pretax-income
														:yearly-deductions {
																								:with-ownership yearly-deductions-with-ownership
																								:no-ownership yearly-deductions-no-ownership
																								}
														:taxable-income {
																						 :with-ownership taxable-income-with-ownership
																						 :no-ownership taxable-income-no-ownership
																						 }
														:average-tax-rate {
																							 :with-ownership (map #(taxes/average-tax-rate %1 :jointly) taxable-income-with-ownership)
																							 :no-ownership (map #(taxes/average-tax-rate %1 :jointly) taxable-income-no-ownership)
																							 }
														:taxes-in-dollars {
																							 :with-ownership (map #(taxes/taxes-in-dollars %1 :jointly) taxable-income-with-ownership)
																							 :no-ownership (map #(taxes/taxes-in-dollars %1 :jointly) taxable-income-no-ownership)
																							 }
													 }
						 post-tax-income {
															:post-tax-income {
																								:with-ownership (mapv - (get-in basic-outputs [:taxable-income :with-ownership]) (get-in basic-outputs [:taxes-in-dollars :with-ownership]))
																								:no-ownership (mapv - (get-in basic-outputs [:taxable-income :no-ownership]) (get-in basic-outputs [:taxes-in-dollars :no-ownership]))
																								}
															}
						 post-tax-post-housing-income {
																					 :post-tax-post-housing-income {
																																					:with-ownership (mapv - (get-in post-tax-income [:post-tax-income :with-ownership]) yearly-principal-payments)
																																					:no-ownership (mapv - (get-in post-tax-income [:post-tax-income :no-ownership]) yearly-rent-payments)
																																					}
																					 }
						 ]
			 {:body (conj basic-outputs post-tax-income post-tax-post-housing-income)}
			 )
			 ))
