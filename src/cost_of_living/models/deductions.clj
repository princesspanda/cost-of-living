(ns cost-of-living.models.deductions)

(defn yearly-deductions [p1-401k p2-401k & other-deductions]
(let [total-401k (repeat (+ p1-401k p2-401k))]
   (mapv + total-401k (reduce #(mapv + %1 %2) (first other-deductions) (rest other-deductions)))
)
)


