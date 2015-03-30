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
0
(read-string (first nums)))
)
)