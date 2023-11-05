/*
 *  JavaSMT is an API wrapper for a collection of SMT solvers.
 *  This file is part of JavaSMT.
 *
 *  Copyright (C) 2007-2016  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.sosy_lab.java_smt.utils.Generators;

import com.google.common.collect.ImmutableList;
import javax.annotation.Nullable;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.basicimpl.AbstractFormulaManager;
import org.sosy_lab.java_smt.basicimpl.AbstractModel;
import org.sosy_lab.java_smt.basicimpl.AbstractProver;
import org.sosy_lab.java_smt.basicimpl.FormulaCreator;

public class UniversalModel extends AbstractModel {
  public UniversalModel(AbstractProver prover, AbstractFormulaManager pFormulaManager) {
    super(prover, pFormulaManager);
  }

  @Override
  public ImmutableList<ValueAssignment> asList() {
    BooleanFormula reg = formulaManager.getBooleanFormulaManager().makeFalse();


    return null;
  }

  @Nullable
  @Override
  protected Object evalImpl(Object formula) {
    return null;
  }
}