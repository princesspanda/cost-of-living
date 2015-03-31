(ns cost-of-living.helpers.schedule)

(defn yearly-interest-cents [schedule nth-year]
(cond
(< nth-year 1) "Year cannot be < 1"
(> (* 12 nth-year) (count schedule)) "Year requested exceeds length of term"
:else (apply + (map :interest_cents (first (drop (dec nth-year) (partition 12 schedule)))))
)
)

(defn yearly-principal-cents [schedule nth-year]
(cond
(< nth-year 1) "Year cannot be < 1"
(> (* 12 nth-year) (count schedule)) "Year requested exceeds length of term"
:else (apply + (map :principal_cents (first (drop (dec nth-year) (partition 12 schedule)))))
)
)

