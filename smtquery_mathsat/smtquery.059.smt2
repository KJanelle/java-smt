;; MathSAT API call trace
;; generated on Mi 25 Okt 2023 14:59:56 CEST
(set-option :global-declarations true)
(set-option :config "verbosity=0")
(set-option :config "proof_generation=false")
(set-option :config "interpolation=false")
(set-option :config "model_generation=true")
(set-option :config "bool_model_generation=false")
(set-option :config "unsat_core_generation=0")
(set-option :config "random_seed=42")
(set-option :config "debug.dump_theory_lemmas=false")
(set-option :config "debug.solver_enabled=true")
(set-option :config "printer.bv_number_format=0")
(set-option :config "printer.fp_number_format=1")
(set-option :config "printer.defines_prefix=")
(set-option :config "printer.model_as_formula=false")
(set-option :config "preprocessor.toplevel_propagation=true")
(set-option :config "preprocessor.toplevel_propagation_limit=0")
(set-option :config "preprocessor.simplification=55")
(set-option :config "preprocessor.ite_minimization=false")
(set-option :config "preprocessor.ite_minimization_call_limit=150000")
(set-option :config "preprocessor.interpolation_ite_elimination=false")
(set-option :config "preprocessor.partial_nnf_conversion=false")
(set-option :config "preprocessor.full_cnf_conversion=false")
(set-option :config "dpll.restart_strategy=0")
(set-option :config "dpll.restart_initial=200")
(set-option :config "dpll.restart_geometric_factor=1.5")
(set-option :config "dpll.store_tlemmas=false")
(set-option :config "dpll.branching_random_frequency=0.001")
(set-option :config "dpll.branching_initial_phase=0")
(set-option :config "dpll.branching_cache_phase=2")
(set-option :config "dpll.branching_random_invalidate_phase_cache=false")
(set-option :config "dpll.branching_random_ignore_polarity=true")
(set-option :config "dpll.ghost_filtering=true")
(set-option :config "dpll.minimize_model=false")
(set-option :config "dpll.allsat_minimize_model=false")
(set-option :config "dpll.allsat_allow_duplicates=false")
(set-option :config "dpll.pop_btpoint_reset_var_order=true")
(set-option :config "dpll.glucose_var_activity=false")
(set-option :config "dpll.glucose_learnt_minimization=false")
(set-option :config "dpll.interpolation_mode=0")
(set-option :config "dpll.proof_simplification=false")
(set-option :config "dpll.preprocessor.mode=0")
(set-option :config "dpll.preprocessor.clause_size_limit=20")
(set-option :config "dpll.preprocessor.subsumption_size_limit=1000")
(set-option :config "dpll.preprocessor.elimination_grow_limit=0")
(set-option :config "dpll.preprocessor.elimination_phase=1")
(set-option :config "dpll.preprocessor.elimination_recent_vars_only=false")
(set-option :config "dpll.preprocessor.try_reelimination=true")
(set-option :config "theory.eq_propagation=true")
(set-option :config "theory.interface_eq_policy=2")
(set-option :config "theory.pairwise_interface_eq=true")
(set-option :config "theory.interface_eq_batch_size=100")
(set-option :config "theory.pure_literal_filtering=false")
(set-option :config "theory.euf.enabled=true")
(set-option :config "theory.euf.dyn_ack=0")
(set-option :config "theory.euf.dyn_ack_threshold=1")
(set-option :config "theory.euf.dyn_ack_limit=1000")
(set-option :config "theory.la.enabled=true")
(set-option :config "theory.la.split_rat_eq=false")
(set-option :config "theory.la.delay_alien=false")
(set-option :config "theory.la.pivoting_greedy_threshold=0")
(set-option :config "theory.la.lazy_expl_threshold=10")
(set-option :config "theory.la.interpolation_laz_use_floor=true")
(set-option :config "theory.la.pure_equality_filtering=true")
(set-option :config "theory.la.detect_euf_fragment=false")
(set-option :config "theory.la.deduction_enabled=true")
(set-option :config "theory.la.laz_equality_elimination=true")
(set-option :config "theory.la.laz_internal_branch_and_bound=true")
(set-option :config "theory.la.laz_internal_branch_and_bound_limit=1")
(set-option :config "theory.la.laz_cuts_from_proofs_mode=3")
(set-option :config "theory.la.laz_enabled=true")
(set-option :config "theory.la.laz_unit_cube_test=true")
(set-option :config "theory.la.interpolation_mode=0")
(set-option :config "theory.bv.enabled=true")
(set-option :config "theory.bv.div_by_zero_mode=0")
(set-option :config "theory.bv.delay_propagated_eqs=true")
(set-option :config "theory.bv.eager=true")
(set-option :config "theory.bv.bit_blast_mode=1")
(set-option :config "theory.bv.interpolation_mode=0")
(set-option :config "theory.bv.proof_simplification=false")
(set-option :config "theory.bv.lazydpll.restart_strategy=0")
(set-option :config "theory.bv.lazydpll.restart_initial=200")
(set-option :config "theory.bv.lazydpll.restart_geometric_factor=1.5")
(set-option :config "theory.bv.lazydpll.branching_random_frequency=0")
(set-option :config "theory.bv.lazydpll.branching_initial_phase=0")
(set-option :config "theory.bv.lazydpll.branching_cache_phase=2")
(set-option :config "theory.bv.lazydpll.branching_random_invalidate_phase_cache=false")
(set-option :config "theory.bv.lazydpll.branching_random_ignore_polarity=true")
(set-option :config "theory.bv.lazydpll.ghost_filtering=false")
(set-option :config "theory.bv.lazydpll.glucose_var_activity=false")
(set-option :config "theory.bv.lazydpll.glucose_learnt_minimization=false")
(set-option :config "theory.arr.enabled=true")
(set-option :config "theory.arr.mode=0")
(set-option :config "theory.arr.lazy_lemmas=false")
(set-option :config "theory.arr.max_ext_lemmas=0")
(set-option :config "theory.arr.enable_ext_polarity=true")
(set-option :config "theory.arr.enable_ext_arg=true")
(set-option :config "theory.arr.enable_witness=true")
(set-option :config "theory.fp.enabled=true")
(set-option :config "theory.fp.mode=1")
(set-option :config "theory.fp.bv_combination_enabled=true")
(set-option :config "theory.fp.bit_blast_mode=2")
(set-option :config "theory.fp.dpll.restart_strategy=3")
(set-option :config "theory.fp.dpll.restart_initial=200")
(set-option :config "theory.fp.dpll.restart_geometric_factor=1.5")
(set-option :config "theory.fp.dpll.branching_random_frequency=0.01")
(set-option :config "theory.fp.dpll.branching_initial_phase=0")
(set-option :config "theory.fp.dpll.branching_cache_phase=1")
(set-option :config "theory.fp.dpll.branching_random_invalidate_phase_cache=false")
(set-option :config "theory.fp.dpll.branching_random_ignore_polarity=true")
(set-option :config "theory.fp.dpll.ghost_filtering=false")
(set-option :config "theory.fp.dpll.glucose_var_activity=true")
(set-option :config "theory.fp.dpll.glucose_learnt_minimization=true")
(set-option :config "theory.fp.acdcl_conflict_generalization=2")
(set-option :config "theory.fp.interpolation_mode=0")
(set-option :config "theory.fp.acdcl_single_propagation_limit=0")
(set-option :config "theory.fp.acdcl_single_generalization_limit=0")
(set-option :config "theory.fp.acdcl_generalization_fair=true")
(set-option :config "theory.fp.minmax_zero_mode=1")
(set-option :config "theory.fp.to_bv_overflow_mode=0")
(set-option :config "theory.na.enabled=true")
(set-option :config "theory.na.permanent_lemmas=true")
(set-option :config "theory.na.eager_lemmas=false")
(set-option :config "theory.na.enable_tangent_lemmas_frontier=true")
(set-option :config "theory.na.tangent_lemmas_rat_approx=0")
(set-option :config "theory.na.tangent_lemmas_limit=0")
(set-option :config "theory.na.tangent_lemmas_enabled=true")
(set-option :config "theory.na.sat_check_enabled=true")
(set-option :config "theory.na.bound_lemmas_enabled=true")
(set-option :config "theory.na.nta_enabled=true")
(set-option :config "theory.na.nta_rat_approx=3")
(set-option :config "theory.na.nta_rat_approx_limit=10")
(set-option :config "theory.na.nta_sat_mode=1")
(set-option :config "theory.na.nta_eager=true")

(push 1)
(declare-fun |id#2@1| () (_ BitVec 32))
(declare-fun |id#0@1| () (_ BitVec 32))
(define-fun .8 () (_ BitVec 32) (_ bv0 32))
(define-fun .10 () (_ BitVec 32) (_ bv10 32))
(define-fun .13 () (_ BitVec 32) (_ bv1 32))
(define-fun .15 () (_ BitVec 32) (_ bv11 32))
(define-fun .20 () (_ BitVec 32) |id#2@1|)
(define-fun .22 () Bool (bvslt .10 .20))
(define-fun .23 () Bool (not .22))
(define-fun .29 () (_ BitVec 1) (_ bv1 1))
(define-fun .30 () (_ BitVec 1) ((_ extract 31 31) .20))
(define-fun .31 () Bool (= .30 .29))
(define-fun .52 () Bool (not .31))
(define-fun .55 () (_ BitVec 32) |id#0@1|)
(define-fun .56 () Bool (bvslt .10 .55))
(define-fun .62 () Bool (not .56))
(define-fun .64 () Bool (= .55 .8))
(define-fun .68 () (_ BitVec 1) ((_ extract 31 31) .55))
(define-fun .69 () Bool (= .68 .29))
(define-fun .89 () Bool (not .69))
(define-fun .92 () Bool (= .20 .55))
(define-fun .101 () Bool (not .92))
(define-fun .119 () (_ BitVec 32) (bvadd .13 .20))
(define-fun .121 () Bool (= .20 .10))
(define-fun .131 () Bool (not .121))
(define-fun .156 () (_ BitVec 32) (_ bv4294967294 32))
(define-fun .157 () (_ BitVec 32) (bvadd .156 .55))
(define-fun .158 () Bool (= .20 .157))
(define-fun .159 () Bool (not .158))
(define-fun .180 () Bool (bvslt .119 .15))
(define-fun .240 () Bool (bvslt .8 .119))
(define-fun .278 () Bool (and .64 .180))
(define-fun .279 () Bool (and .89 .278))
(define-fun .280 () Bool (and .52 .279))
(define-fun .281 () Bool (and .62 .280))
(define-fun .282 () Bool (and .131 .281))
(define-fun .322 () Bool (and .159 .282))
(define-fun .323 () Bool (and .101 .322))
(define-fun .388 () Bool (and .158 .323))
(define-fun .389 () Bool (and .240 .388))
(define-fun .390 () Bool (and .23 .389))
(define-fun .391 () Bool (and .64 .390))
(assert .391)
(check-sat)
