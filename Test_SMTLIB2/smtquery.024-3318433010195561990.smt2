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
(assert (and (= |id#2@1| (_ bv10 32)) (and (and (and (and (and (and (bvsle (_ bv0 32) |id#0@1|) (= (bvadd |id#2@1| (_ bv1 32)) (_ bv11 32))) (bvsle |id#2@1| (_ bv10 32))) (bvsle (_ bv0 32) |id#2@1|)) (= (bvadd |id#2@1| (_ bv1 32)) (bvsub |id#0@1| (_ bv1 32)))) (bvsle |id#0@1| (_ bv10 32))) (not (= |id#0@1| |id#2@1|)))))
(check-sat)
(exit)
(reset)
(set-logic ALL)
