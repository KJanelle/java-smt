(declare-fun |id#2@1| () (_ BitVec 32))
(declare-fun |id#0@1| () (_ BitVec 32))
(assert (and (bvsle |id#2@1| #x0000000a)
     (bvsle #x00000000 |id#2@1|)
     (bvsle #x00000000 |id#0@1|)
     (bvsle |id#0@1| #x0000000a)
     (= |id#0@1| |id#2@1|)))
(check-sat)
