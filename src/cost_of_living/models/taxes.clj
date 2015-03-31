(ns cost-of-living.models.taxes
(:require [cost-of-living.helpers.param_utils :as utils]))

; ripped off of here: http://www.efile.com/tax-rate/federal-income-tax-rates/
(def federal-tax-schedule
{
 0.10 {:single 9075   :jointly 18150  :separate 9075   :hoh 12950}
 0.15 {:single 36900  :jointly 73800  :separate 36900  :hoh 49400}
 0.25 {:single 89350  :jointly 148850 :separate 74425  :hoh 127550}
 0.28 {:single 186350 :jointly 226850 :separate 113425 :hoh 206600}
 0.33 {:single 405100 :jointly 405100 :separate 202550 :hoh 405100}
 0.35 {:single 406750 :jointly 457600 :separate 228800 :hoh 432200}
 0.396 {:single (Double/POSITIVE_INFINITY) :jointly (Double/POSITIVE_INFINITY) :separate (Double/POSITIVE_INFINITY) :hoh (Double/POSITIVE_INFINITY)}
 }
)
(def rates (sort (keys federal-tax-schedule)))

(def schedule-by-status
(reduce
(fn [acc filing-status]
(conj acc [filing-status (zipmap rates (map filing-status (vals federal-tax-schedule)))])
)
{}
[:single :jointly :separate :hoh]
) )


(defn calculate-brackets [income status]
(:brackets
(reduce
(fn [acc rate]
(let [income-limit ((status schedule-by-status) rate)
			remaining-income (:remaining_income acc)
			brackets (:brackets acc)
			income-in-slice (if (empty? brackets) (min income-limit remaining-income) (- (min income income-limit) (apply + (map last brackets))) )
			]
(if (> remaining-income 0)
(conj acc
			{:remaining_income (max (- remaining-income income-in-slice) 0)
			 :brackets (conj brackets [rate income-in-slice])})
acc
)))
{
 :remaining_income income
 :brackets []
 }
rates
)
)
)

(defn average-tax-rate [income status]
(let [brackets (calculate-brackets income status)]
(utils/round2 (/ (apply + (map #(apply * %1) brackets)) income)))
)

(defn taxes-in-dollars [ti status]
(Math/round (* ti (average-tax-rate ti status)))
)
