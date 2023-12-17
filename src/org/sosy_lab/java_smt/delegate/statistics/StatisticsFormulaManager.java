// This file is part of JavaSMT,
// an API wrapper for a collection of SMT solvers:
// https://github.com/sosy-lab/java-smt
//
// SPDX-FileCopyrightText: 2020 Dirk Beyer <https://www.sosy-lab.org>
//
// SPDX-License-Identifier: Apache-2.0

package org.sosy_lab.java_smt.delegate.statistics;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.sosy_lab.common.Appender;
import org.sosy_lab.common.Appenders;
import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.java_smt.api.ArrayFormulaManager;
import org.sosy_lab.java_smt.api.BitvectorFormulaManager;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.BooleanFormulaManager;
import org.sosy_lab.java_smt.api.EnumerationFormulaManager;
import org.sosy_lab.java_smt.api.FloatingPointFormulaManager;
import org.sosy_lab.java_smt.api.Formula;
import org.sosy_lab.java_smt.api.FormulaManager;
import org.sosy_lab.java_smt.api.FormulaType;
import org.sosy_lab.java_smt.api.FunctionDeclaration;
import org.sosy_lab.java_smt.api.IntegerFormulaManager;
import org.sosy_lab.java_smt.api.QuantifiedFormulaManager;
import org.sosy_lab.java_smt.api.RationalFormulaManager;
import org.sosy_lab.java_smt.api.SLFormulaManager;
import org.sosy_lab.java_smt.api.SolverException;
import org.sosy_lab.java_smt.api.StringFormulaManager;
import org.sosy_lab.java_smt.api.Tactic;
import org.sosy_lab.java_smt.api.UFManager;
import org.sosy_lab.java_smt.api.visitors.FormulaTransformationVisitor;
import org.sosy_lab.java_smt.api.visitors.FormulaVisitor;
import org.sosy_lab.java_smt.api.visitors.TraversalProcess;
import org.sosy_lab.java_smt.basicimpl.Generator;

class StatisticsFormulaManager implements FormulaManager {

  private final FormulaManager delegate;
  private final SolverStatistics stats;

  protected StatisticsFormulaManager(FormulaManager pDelegate, SolverStatistics pStats) {
    delegate = checkNotNull(pDelegate);
    stats = checkNotNull(pStats);
  }

  @Override
  public IntegerFormulaManager getIntegerFormulaManager() {
    return new StatisticsIntegerFormulaManager(delegate.getIntegerFormulaManager(), stats);
  }

  @Override
  public RationalFormulaManager getRationalFormulaManager() {
    return new StatisticsRationalFormulaManager(delegate.getRationalFormulaManager(), stats);
  }

  @Override
  public BooleanFormulaManager getBooleanFormulaManager() {
    return new StatisticsBooleanFormulaManager(delegate.getBooleanFormulaManager(), stats);
  }

  @Override
  public ArrayFormulaManager getArrayFormulaManager() {
    return new StatisticsArrayFormulaManager(delegate.getArrayFormulaManager(), stats);
  }

  @Override
  public BitvectorFormulaManager getBitvectorFormulaManager() {
    return new StatisticsBitvectorFormulaManager(delegate.getBitvectorFormulaManager(), stats);
  }

  @Override
  public FloatingPointFormulaManager getFloatingPointFormulaManager() {
    return new StatisticsFloatingPointFormulaManager(
        delegate.getFloatingPointFormulaManager(), stats);
  }

  @Override
  public UFManager getUFManager() {
    return new StatisticsUFManager(delegate.getUFManager(), stats);
  }

  @Override
  public SLFormulaManager getSLFormulaManager() {
    return new StatisticsSLFormulaManager(delegate.getSLFormulaManager(), stats);
  }

  @Override
  public QuantifiedFormulaManager getQuantifiedFormulaManager() {
    return new StatisticsQuantifiedFormulaManager(delegate.getQuantifiedFormulaManager(), stats);
  }

  @Override
  public StringFormulaManager getStringFormulaManager() {
    return new StatisticsStringFormulaManager(delegate.getStringFormulaManager(), stats);
  }

  @Override
  public EnumerationFormulaManager getEnumerationFormulaManager() {
    return new StatisticsEnumerationFormulaManager(delegate.getEnumerationFormulaManager(), stats);
  }

  @Override
  public <T extends Formula> T makeVariable(FormulaType<T> pFormulaType, String pName)
      throws IOException {
    return delegate.makeVariable(pFormulaType, pName);
  }

  @Override
  public <T extends Formula> T makeApplication(
      FunctionDeclaration<T> pDeclaration, List<? extends Formula> pArgs) {
    return delegate.makeApplication(pDeclaration, pArgs);
  }

  @Override
  public <T extends Formula> T makeApplication(
      FunctionDeclaration<T> pDeclaration, Formula... pArgs) {
    return delegate.makeApplication(pDeclaration, pArgs);
  }

  @Override
  public <T extends Formula> FormulaType<T> getFormulaType(T pFormula) {
    return delegate.getFormulaType(pFormula);
  }

  @Override
  public BooleanFormula parse(String pS) throws IllegalArgumentException {
    return delegate.parse(pS);
  }

  /**
   * Parses an SMT-LIB2 file and translates it into an equivalent BooleanFormula constraint
   * @param pS name of the file that contains the SMT-LIB2
   * @return BooleanFormula equivalent to the SMT-LIB2 in file
   * @throws IOException if file can't be read
   */
  @Override
  public BooleanFormula universalParse(String pS)
      throws IllegalArgumentException,
          IOException,
          SolverException,
          InterruptedException,
          InvalidConfigurationException {
    return delegate.universalParse(pS);
  }

  /**
   * Parses an SMT-LIB2 String and translates it into an equivalent BooleanFormula constraint
   * @param pString SMT-LIB2 formula as String that will be parsed
   * @return BooleanFormula equivalent to the SMT-LIB2 string
   */
  @Override
  public BooleanFormula universalParseFromString(String pString)
      throws IOException, SolverException, InterruptedException, InvalidConfigurationException {
    return delegate.universalParseFromString(pString);
  }

  /**
   * Calls the dumpSMTLIB2 method from the Generator, which will write the assembled SMT-LIB2 to a
   * file 'Out.smt2'
   * @throws IOException if writing to file fails
   */
  @Override
  public void dumpSMTLIB2() throws IOException {
    delegate.dumpSMTLIB2();
  }

  @Override
  public Appender dumpFormula(BooleanFormula pT) {
    return new Appenders.AbstractAppender() {
      @Override
      public void appendTo(Appendable out) throws IOException {
        String dump;
        dump = delegate.dumpFormula(pT).toString(); // the work is done here
        out.append(dump);
      }
    };
  }

  @Override
  public BooleanFormula applyTactic(BooleanFormula pInput, Tactic pTactic)
      throws InterruptedException, IOException {
    return delegate.applyTactic(pInput, pTactic);
  }

  @Override
  public <T extends Formula> T simplify(T pInput) throws InterruptedException {
    return delegate.simplify(pInput);
  }

  @Override
  public <R> R visit(Formula pF, FormulaVisitor<R> pFormulaVisitor) throws IOException {
    return delegate.visit(pF, pFormulaVisitor);
  }

  @Override
  public void visitRecursively(Formula pF, FormulaVisitor<TraversalProcess> pFormulaVisitor)
      throws IOException {
    delegate.visitRecursively(pF, pFormulaVisitor);
  }

  @Override
  public <T extends Formula> T transformRecursively(
      T pF, FormulaTransformationVisitor pFormulaVisitor) throws IOException {
    return delegate.transformRecursively(pF, pFormulaVisitor);
  }

  @Override
  public ImmutableMap<String, Formula> extractVariables(Formula pF) throws IOException {
    return delegate.extractVariables(pF);
  }

  @Override
  public ImmutableMap<String, Formula> extractVariablesAndUFs(Formula pF) throws IOException {
    return delegate.extractVariablesAndUFs(pF);
  }

  @Override
  public <T extends Formula> T substitute(
      T pF, Map<? extends Formula, ? extends Formula> pFromToMapping) throws IOException {
    return delegate.substitute(pF, pFromToMapping);
  }

  @Override
  public BooleanFormula translateFrom(BooleanFormula pFormula, FormulaManager pOtherContext) {
    return delegate.translateFrom(pFormula, pOtherContext);
  }

  @Override
  public boolean isValidName(String pVariableName) {
    return delegate.isValidName(pVariableName);
  }

  @Override
  public String escape(String pVariableName) {
    return delegate.escape(pVariableName);
  }

  @Override
  public String unescape(String pVariableName) {
    return delegate.unescape(pVariableName);
  }
}
