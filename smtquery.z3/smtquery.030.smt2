(declare-fun |id#0@1| () (_ BitVec 32))
(declare-fun |id#2@1| () (_ BitVec 32))
(assert (and (= |id#2@1| #x0000000a)
     (not (= (bvadd |id#2@1| #x00000001) (bvsub |id#0@1| #x00000001)))
     (bvsle |id#2@1| #x0000000a)
     (bvsle #x00000000 |id#2@1|)
     (not (= |id#0@1| |id#2@1|))
     (bvsle #x00000000 |id#0@1|)
     (= (bvadd |id#2@1| #x00000001) #x0000000b)
     (bvsle |id#0@1| #x0000000a)))
(check-sat)
