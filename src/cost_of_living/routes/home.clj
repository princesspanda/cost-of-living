(ns cost-of-living.routes.home
  (:require [compojure.core :refer :all]
            [cost-of-living.views.layout :as layout]
						[cost-of-living.models.amort :as amort]
						[cost-of-living.helpers.param_utils :as utils])
)

(defn home []
  (layout/common [:h1 "Hello World!"]))

(defroutes home-routes
  (GET "/" [] (home))
	(GET "/schedule" [principal
										interest-rate]
			 {:body (amort/amort-schedule (utils/parse-int principal)  1  (utils/parse-double interest-rate))}))
