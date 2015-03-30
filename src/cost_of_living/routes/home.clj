(ns cost-of-living.routes.home
  (:require [compojure.core :refer :all]
            [cost-of-living.views.layout :as layout]
						[cost-of-living.models.amort :as amort])
)

(defn home []
  (layout/common [:h1 "Hello World!"]))

(defroutes home-routes
  (GET "/" [] (home))
	(GET "/schedule" [] {:body (amort/amort-schedule 1000 1 3.125)}))
