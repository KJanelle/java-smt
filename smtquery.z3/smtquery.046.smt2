(declare-fun |id#0@1| () (_ BitVec 32))
(declare-fun |id#2@1| () (_ BitVec 32))
(assert (and (bvslt #x00000000 (bvadd |id#2@1| #x00000001))
     (bvslt (bvadd |id#2@1| #x00000001) #x0000000b)
     (bvsle |id#2@1| #x0000000a)
     (bvsle #x00000000 |id#2@1|)
     (not (= |id#0@1| |id#2@1|))
     (bvsle #x00000000 |id#0@1|)
     (not (= (bvadd |id#2@1| #x00000001) #x0000000b))
     (bvsle |id#0@1| #x0000000a)
     (= (bvsub |id#0@1| #x00000001) #xffffffff)))
(check-sat)
