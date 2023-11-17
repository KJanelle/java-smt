(set-logic QF_LIA)
(set-info :source | Leonardo de Moura |)
(set-info :smt-lib-version 2.0)
(set-info :category "check")
(set-info :status unsat)
(set-info :notes |This benchmark is designed to check if the integer DP is complete.|)
(declare-fun x1 () Int)
(declare-fun x2 () Int)
(declare-fun x3 () Int)
(declare-fun x4 () Int)
(assert (and (= (+ x1 x2) 8) (= x1 (* 3 x3)) (= x2 (* 6 x4))))
(check-sat)
(exit)
