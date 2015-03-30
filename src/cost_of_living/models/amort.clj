(ns cost-of-living.models.amort)


(defn in-cents
"Round a double to the # of cents"
[d]
(let [factor 100]
(Math/round (* d factor))))

(defn calculate-payment [principal term-months interest-rate]
(let [monthly-interest (/ interest-rate 1200)
			exp (Math/pow (+ 1 monthly-interest) term-months)]
(in-cents (/ (* monthly-interest principal exp) (- exp 1)))
)
)

(defn amort-schedule [principal term-years interest-rate]
(let [term-months (* 12 term-years)
			monthly-payment-cents (calculate-payment principal term-months interest-rate)
			monthly-interest-pct (/ interest-rate 1200)
			initial-principal-cents (in-cents (double principal))]
(reduce
(fn [acc n]
(let [balance-cents (if (nil? (last acc)) initial-principal-cents (:balance_cents (last acc)))
			interest-cents (* monthly-interest-pct balance-cents)
			principal-cents (- monthly-payment-cents interest-cents)
			]
(conj acc {:month                n
					 :payment_cents        monthly-payment-cents
					 :principal_cents      (Math/round principal-cents)
					 :interest_cents       (Math/round interest-cents)
					 :total_interest_cents (apply + (Math/round interest-cents) (map :interest_cents acc))
					 :balance_cents        (Math/round (- balance-cents principal-cents))}))
)
[]
(range 1 (inc term-months)))))


