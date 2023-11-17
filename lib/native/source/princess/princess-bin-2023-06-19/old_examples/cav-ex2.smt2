(set-logic AUFLIA)

(declare-fun f (Int) Int)
(declare-fun g (Int Int) Int)
(declare-fun h (Int) Int)
(declare-fun a () Int)
(declare-fun b () Int)

(assert (forall ((x1 Int) (x2 Int)) (or (= (g x1 x2) 0) (= (h x2) 0))))
(assert (forall ((x1 Int)) (<= (+ (g (f x1) b) 1) (f x1))))
(assert (= (h b) 1))
(assert (= (f a) 0))

(check-sat)
(exit)