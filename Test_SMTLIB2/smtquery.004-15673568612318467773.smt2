(reset)
(set-logic ALL)
(declare-fun __A () Bool)
(declare-fun |id#1@1| () (_ BitVec 32))
(declare-fun |id#2@1| () (_ BitVec 32))
(declare-fun __string__ (Int) (_ BitVec 32))
(declare-fun Integer_%_ (Int Int) Int)
(declare-fun Integer_*_ (Int Int) Int)
(declare-fun Integer_/_ (Int Int) Int)
(push 1)
(assert (and (bvsle (_ bv0 32) |id#2@1|) (bvsle |id#2@1| (_ bv10 32))))
(check-sat)
; (partial-model)
; (get-value ...)
; (get-value ...)
(push 1)
(assert (or (not __A) (not (= |id#2@1| (_ bv10 32)))))
(push 1)
(assert __A)
(check-sat)
(pop 1)
(pop 1)
(exit)
(reset)
(set-logic ALL)
