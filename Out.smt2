(declare-const p Bool)
(assert (= true (or p p)))
(check-sat)
(get-model)
(exit)