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
(assert (bvslt (_ bv10 32) |id#0@1|))
(check-sat)
; (partial-model)
; (get-value ...)
; (get-value ...)
(push 1)
(assert (or (not __A) (not (= |id#0@1| (_ bv2147483647 32)))))
(push 1)
(assert __A)
(check-sat)
(pop 1)
(pop 1)
(exit)
(reset)
(set-logic ALL)
