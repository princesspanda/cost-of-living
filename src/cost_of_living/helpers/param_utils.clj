(ns cost-of-living.helpers.param_utils)

(defn parse-int [str]
 "Parses a string into an int, ignore non-digit characters"
 (let [nums (re-seq #"[\d]+" str)]
  (if (empty? nums)
	0
	(read-string (first nums)))))

(defn parse-double [str]
"Parses a string into a double"
(let [nums (re-seq #"[\d]+\.[\d]+" str)]
(if (empty? nums)
0.0
(read-string (first nums)))
)
)

(defn in-cents [d]
"Round a double to the # of cents"
(let [factor 100.0]
(Math/round (* d factor))))


(defn in-dollars [x-cents]
"Takes cents, divides by 100, and rounds"
(Math/round (/ x-cents 100.0))
)

(defn round2
[d]
(/ (in-cents d) 100.0))