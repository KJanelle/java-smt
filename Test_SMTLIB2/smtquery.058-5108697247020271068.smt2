(reset)
(set-logic ALL)
(declare-fun __A () Bool)
(declare-fun |id#1@1| () (_ BitVec 32))
(declare-fun |id#2@1| () (_ BitVec 32))
(declare-fun |id#0@1| () (_ BitVec 32))
(declare-fun __string__ (Int) (_ BitVec 32))
(declare-fun Integer_%_ (Int Int) Int)
(declare-fun Integer_*_ (Int Int) Int)
(declare-fun Integer_/_ (Int Int) Int)
(push 1)
(assert (and (and (and (and (and (and (and (and (and (bvslt (bvadd |id#2@1| (_ bv1 32)) (_ bv11 32)) (bvsle (_ bv0 32) |id#0@1|)) (not (= (bvadd |id#2@1| (_ bv1 32)) (_ bv11 32)))) (bvsle (bvsub |id#0@1| (_ bv1 32)) (_ bv4294967295 32))) (= |id#0@1| |id#2@1|)) (bvslt (_ bv0 32) (bvadd |id#2@1| (_ bv1 32)))) (bvsle |id#2@1| (_ bv10 32))) (bvsle (_ bv0 32) |id#2@1|)) (bvsle |id#0@1| (_ bv10 32))) (not (= (bvsub |id#0@1| (_ bv1 32)) (_ bv4294967295 32)))))
(check-sat)
(exit)
(reset)
(set-logic ALL)
