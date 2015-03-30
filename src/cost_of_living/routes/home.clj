(ns cost-of-living.routes.home
  (:require [compojure.core :refer :all]
            [cost-of-living.views.layout :as layout]
						[cost-of-living.models.amort :as amort]
						[cost-of-living.helpers.schedule :as schedule]
						[cost-of-living.helpers.param_utils :as utils])
)

(defn home []
  (layout/common [:h1 "Hello World!"]))

(defroutes home-routes
  (GET "/" [] (home))
	(GET "/schedule" [principal
										interest-rate]
			 (let [schedule (amort/amort-schedule (utils/parse-int principal)  15  (utils/parse-double interest-rate))]
			 {:body {
							 :schedule schedule
							 :mortgage-interest-deductions (reduce #(conj %1 { %2 (schedule/yearly-interest-cents schedule %2)} ) {} (range 1 16))
							 }}
			 )
			 ))
