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

package org.sosy_lab.java_smt.test;

import static com.google.common.truth.TruthJUnit.assume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import org.sosy_lab.java_smt.SolverContextFactory.Solvers;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.NumeralFormula;
import org.sosy_lab.java_smt.api.NumeralFormula.IntegerFormula;
import org.sosy_lab.java_smt.api.NumeralFormula.RationalFormula;
import org.sosy_lab.java_smt.basicimpl.Generator;

public class NumeralSMTLIB2GeneratorTest extends SolverBasedTest0.ParameterizedSolverBasedTest0 {

  /** Z3 runs only when executed separately from other solvers */
  public void clearGenerator() {
    Generator.lines.delete(0, Generator.lines.length());
    Generator.registeredVariables.clear();
    Generator.executedAggregator.clear();
  }

  @Test
  public void testMakeVariableInteger() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeVariable("a");
    IntegerFormula b = imgr.makeVariable("b");
    BooleanFormula constraint = imgr.equal(a, b);
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResult =
        "(declare-const a Int)\n" + "(declare-const b Int)\n" + "(assert (= a b))\n";
    Assert.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testMakeVariableRational() {
    requireRationals();
    clearGenerator();
    NumeralFormula a = Objects.requireNonNull(rmgr).makeVariable("a");
    NumeralFormula b = rmgr.makeVariable("b");
    BooleanFormula constraint = rmgr.equal(a, b);
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResult =
        "(declare-const a Real)\n" + "(declare-const b Real)\n" + "(assert (= a b))\n";
    Assert.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testIntegerMakeNumberEqualsAndAdd() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);

    BooleanFormula constraint = imgr.equal(a, imgr.add(b, imgr.add(c, e)));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResult = "(assert (= 1 (+ (- 5) (+ 3 2147483647))))\n";
    Assert.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testRationalsMakeNumberEqualsAndAdd() {
    requireRationals();
    clearGenerator();
    RationalFormula a = Objects.requireNonNull(rmgr).makeNumber(-1);
    RationalFormula c = rmgr.makeNumber("3.4");
    RationalFormula e = rmgr.makeNumber(2147483.647);
    BooleanFormula constraint = rmgr.equal(a, rmgr.add(c, e));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (= -1 (+ 17/5 2147483647/1000)))\n";
    String expectedResultSMTInterpol = "(assert (= (- 1.0) (+ 3.4 2147483.647)))\n";
    String expectedResultCVC4 = "(assert (= (- 1) (+ (/ 17 5) (/ 2147483647 1000))))\n";
    String expectedResultCVC5 = "(assert (= (- 1.0) (+ (/ 17 5) (/ 2147483647 1000))))\n";
    String expectedResultZ3 = "(assert (= (- 1.0) (+ (/ 17.0 5.0) (/ 2147483647.0 1000.0))))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5)
            || actualResult.equals(expectedResultSMTInterpol)
            || actualResult.equals(expectedResultCVC4)
            || actualResult.equals(expectedResultCVC5)
            || actualResult.equals(expectedResultZ3));
  }

  @Test
  public void testIntegerSubtract() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);

    BooleanFormula constraint = imgr.equal(a, imgr.subtract(b, imgr.subtract(c, e)));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResult = "(assert (= 1 (- (- 5) (- 3 2147483647))))\n";
    Assert.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testRationalSubtract() {
    requireRationals();
    clearGenerator();
    RationalFormula a = Objects.requireNonNull(rmgr).makeNumber(-1);
    RationalFormula c = rmgr.makeNumber("3.4");
    RationalFormula e = rmgr.makeNumber(2147483.647);
    BooleanFormula constraint = rmgr.equal(a, rmgr.subtract(c, e));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (= -1 (- 17/5 2147483647/1000)))\n";
    String expectedResultSMTInterpol = "(assert (= (- 1.0) (- 3.4 2147483.647)))\n";
    String expectedResultCVC4 = "(assert (= (- 1) (- (/ 17 5) (/ 2147483647 1000))))\n";
    String expectedResultCVC5 = "(assert (= (- 1.0) (- (/ 17 5) (/ 2147483647 1000))))\n";
    String expectedResultZ3 = "(assert (= (- 1.0) (- (/ 17.0 5.0) (/ 2147483647.0 1000.0))))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5)
            || actualResult.equals(expectedResultSMTInterpol)
            || actualResult.equals(expectedResultCVC4)
            || actualResult.equals(expectedResultCVC5)
            || actualResult.equals(expectedResultZ3));
  }

  @Test
  public void testIntegerNegate() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);
    BooleanFormula constraint =
        imgr.equal(
            imgr.subtract(imgr.negate(b), imgr.negate(a)),
            imgr.subtract(imgr.negate(c), imgr.negate(e)));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResult = "(assert (= (- (- (- 5)) (- 1)) (- (- 3) (- 2147483647))))\n";
    Assert.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testRationalNegate() {
    requireRationals();
    clearGenerator();
    RationalFormula a = Objects.requireNonNull(rmgr).makeNumber(-1);
    RationalFormula c = rmgr.makeNumber("3.4");
    RationalFormula e = rmgr.makeNumber(2147483.647);
    BooleanFormula constraint =
        rmgr.equal(rmgr.negate(a), rmgr.subtract(rmgr.negate(c), rmgr.negate(e)));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (= (- -1) (- (- 17/5) (- 2147483647/1000))))\n";
    String expectedResultSMTInterpol = "(assert (= (- (- 1.0)) (- (- 3.4) (- 2147483.647))))\n";
    String expectedResultCVC4 = "(assert (= (- (- 1)) (- (- (/ 17 5)) (- (/ 2147483647 1000)))))\n";
    String expectedResultCVC5 =
        "(assert (= (- (- 1.0)) (- (- (/ 17 5)) (- (/ 2147483647 1000)))))\n";
    String expectedResultZ3 =
        "(assert (= (- (- 1.0)) (- (- (/ 17.0 5.0)) (- (/ 2147483647.0 1000.0)))))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5)
            || actualResult.equals(expectedResultSMTInterpol)
            || actualResult.equals(expectedResultCVC4)
            || actualResult.equals(expectedResultCVC5)
            || actualResult.equals(expectedResultZ3));
  }

  @Test
  public void testIntegerSum() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);
    List<IntegerFormula> d = new ArrayList<>();
    d.add(a);
    d.add(b);
    d.add(c);
    d.add(e);

    BooleanFormula constraint = imgr.equal(e, imgr.sum(d));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (= 2147483647 (+ 1 -5 3 2147483647)))\n";
    String expectedResultOthers = "(assert (= 2147483647 (+ 1 (- 5) 3 2147483647)))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5) || actualResult.equals(expectedResultOthers));
  }

  @Test
  public void testRationalSum() {
    requireRationals();
    clearGenerator();
    RationalFormula a = Objects.requireNonNull(rmgr).makeNumber(-1);
    RationalFormula c = rmgr.makeNumber("3.4");
    RationalFormula e = rmgr.makeNumber(2147483.647);
    List<NumeralFormula> d = new ArrayList<>();
    d.add(a);
    d.add(c);
    d.add(e);

    BooleanFormula constraint = rmgr.equal(a, rmgr.sum(d));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (= -1 (+ -1 17/5 2147483647/1000)))\n";
    String expectedResultSMTInterpol = "(assert (= (- 1.0) (+ (- 1.0) 3.4 2147483.647)))\n";
    String expectedResultCVC4 = "(assert (= (- 1) (+ (- 1) (/ 17 5) (/ 2147483647 1000))))\n";
    String expectedResultCVC5 = "(assert (= (- 1.0) (+ (- 1.0) (/ 17 5) (/ 2147483647 1000))))\n";
    String expectedResultZ3 =
        "(assert (= (- 1.0) (+ (- 1.0) (/ 17.0 5.0) (/ 2147483647.0 1000.0))))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5)
            || actualResult.equals(expectedResultSMTInterpol)
            || actualResult.equals(expectedResultCVC4)
            || actualResult.equals(expectedResultCVC5)
            || actualResult.equals(expectedResultZ3));
  }

  @Test
  public void testIntegerDivide() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);

    BooleanFormula constraint = imgr.equal(a, imgr.divide(b, imgr.divide(c, e)));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResult = "(assert (= 1 (div (- 5) (div 3 2147483647))))\n";
    Assert.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testRationalDivide() {
    requireRationals();
    clearGenerator();
    RationalFormula a = Objects.requireNonNull(rmgr).makeNumber(-1);
    RationalFormula c = rmgr.makeNumber("3.4");
    RationalFormula e = rmgr.makeNumber(2147483.647);
    BooleanFormula constraint = rmgr.equal(a, rmgr.divide(c, e));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (= -1 (div 17/5 2147483647/1000)))\n";
    String expectedResultSMTInterpol = "(assert (= (- 1.0) (div 3.4 2147483.647)))\n";
    String expectedResultCVC4 = "(assert (= (- 1) (div (/ 17 5) (/ 2147483647 1000))))\n";
    String expectedResultCVC5 = "(assert (= (- 1.0) (div (/ 17 5) (/ 2147483647 1000))))\n";
    String expectedResultZ3 = "(assert (= (- 1.0) (div (/ 17.0 5.0) (/ 2147483647.0 1000.0))))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5)
            || actualResult.equals(expectedResultSMTInterpol)
            || actualResult.equals(expectedResultCVC4)
            || actualResult.equals(expectedResultCVC5)
            || actualResult.equals(expectedResultZ3));
  }

  /** not available for Mathsat */
  @Test
  public void testIntegerModulo() {
    requireIntegers();
    assume()
        .withMessage("Solver %s does not support modulo. ", solverToUse())
        .that(solverToUse())
        .isNotEqualTo(Solvers.MATHSAT5);
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);

    BooleanFormula constraint = imgr.equal(a, imgr.modulo(b, imgr.modulo(c, e)));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultOthers = "(assert (= 1 (mod (- 5) (mod 3 2147483647))))\n";
    String expectedResultYices = "(assert (= 1 1))\n";
    Assert.assertTrue(
        actualResult.equals(expectedResultOthers) || actualResult.equals(expectedResultYices));
  }

  @Test
  public void testIntegerMultiply() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);

    BooleanFormula constraint = imgr.equal(a, imgr.multiply(b, imgr.multiply(c, e)));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResult = "(assert (= 1 (* (- 5) (* 3 2147483647))))\n";
    Assert.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testRationalMultiply() {
    requireRationals();
    clearGenerator();
    RationalFormula a = Objects.requireNonNull(rmgr).makeNumber(-1);
    RationalFormula c = rmgr.makeNumber("3.4");
    RationalFormula e = rmgr.makeNumber(2147483.647);
    BooleanFormula constraint = rmgr.equal(a, rmgr.multiply(c, e));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (= -1 (* 17/5 2147483647/1000)))\n";
    String expectedResultSMTInterpol = "(assert (= (- 1.0) (* 3.4 2147483.647)))\n";
    String expectedResultCVC4 = "(assert (= (- 1) (* (/ 17 5) (/ 2147483647 1000))))\n";
    String expectedResultCVC5 = "(assert (= (- 1.0) (* (/ 17 5) (/ 2147483647 1000))))\n";
    String expectedResultZ3 = "(assert (= (- 1.0) (* (/ 17.0 5.0) (/ 2147483647.0 1000.0))))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5)
            || actualResult.equals(expectedResultSMTInterpol)
            || actualResult.equals(expectedResultCVC4)
            || actualResult.equals(expectedResultCVC5)
            || actualResult.equals(expectedResultZ3));
  }

  @Test
  public void testIntegerDistinct() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);
    List<IntegerFormula> d = new ArrayList<>();
    d.add(a);
    d.add(b);
    d.add(c);
    d.add(e);

    BooleanFormula constraint = imgr.distinct(d);
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (distinct 1 -5 3 2147483647))\n";
    String expectedResultOthers = "(assert (distinct 1 (- 5) 3 2147483647))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5) || actualResult.equals(expectedResultOthers));
  }

  @Test
  public void testRationalDistinct() {
    requireRationals();
    clearGenerator();
    RationalFormula a = Objects.requireNonNull(rmgr).makeNumber(-1);
    RationalFormula c = rmgr.makeNumber("3.4");
    RationalFormula e = rmgr.makeNumber(2147483.647);
    List<NumeralFormula> d = new ArrayList<>();
    d.add(a);
    d.add(c);
    d.add(e);

    BooleanFormula constraint = rmgr.distinct(d);
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (distinct -1 17/5 2147483647/1000))\n";
    String expectedResultSMTInterpol = "(assert (distinct (- 1.0) 3.4 2147483.647))\n";
    String expectedResultCVC4 = "(assert (distinct (- 1) (/ 17 5) (/ 2147483647 1000)))\n";
    String expectedResultCVC5 = "(assert (distinct (- 1.0) (/ 17 5) (/ 2147483647 1000)))\n";
    String expectedResultZ3 = "(assert (distinct (- 1.0) (/ 17.0 5.0) (/ 2147483647.0 1000.0)))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5)
            || actualResult.equals(expectedResultSMTInterpol)
            || actualResult.equals(expectedResultCVC4)
            || actualResult.equals(expectedResultCVC5)
            || actualResult.equals(expectedResultZ3));
  }

  @Test
  public void testIntegerGreaterThan() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);

    BooleanFormula constraint = bmgr.and(imgr.greaterThan(a, b), imgr.greaterThan(c, e));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (> 3 2147483647))\n";
    String expectedResultOthers = "(assert (and (> 1 (- 5)) (> 3 2147483647)))\n";
    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5) || actualResult.equals(expectedResultOthers));
  }

  @Test
  public void testRationalGreaterThan() {
    requireRationals();
    clearGenerator();
    RationalFormula a = Objects.requireNonNull(rmgr).makeNumber(-1);
    RationalFormula c = rmgr.makeNumber("3.4");
    RationalFormula e = rmgr.makeNumber(2147483.647);

    BooleanFormula constraint = bmgr.and(rmgr.greaterThan(a, c), rmgr.greaterThan(c, e));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (> -1 17/5))\n";
    String expectedResultSMTInterpol = "(assert (and (> (- 1.0) 3.4) (> 3.4 2147483.647)))\n";
    String expectedResultCVC4 =
        "(assert (and (> (- 1) (/ 17 5)) (> (/ 17 5) (/ 2147483647 1000))))\n";
    String expectedResultCVC5 =
        "(assert (and (> (- 1.0) (/ 17 5)) (> (/ 17 5) (/ 2147483647 1000))))\n";
    String expectedResultZ3 =
        "(assert (and (> (- 1.0) (/ 17.0 5.0)) (> (/ 17.0 5.0) (/ " + "2147483647.0 1000.0))))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5)
            || actualResult.equals(expectedResultSMTInterpol)
            || actualResult.equals(expectedResultCVC4)
            || actualResult.equals(expectedResultCVC5)
            || actualResult.equals(expectedResultZ3));
  }

  @Test
  public void testIntegerGreaterOrEquals() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);

    BooleanFormula constraint = bmgr.and(imgr.greaterOrEquals(a, b), imgr.greaterOrEquals(c, e));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (>= 3 2147483647))\n";
    String expectedResultOthers = "(assert (and (>= 1 (- 5)) (>= 3 2147483647)))\n";
    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5) || actualResult.equals(expectedResultOthers));
  }

  @Test
  public void testRationalGreaterOrEquals() {
    requireRationals();
    clearGenerator();
    RationalFormula a = Objects.requireNonNull(rmgr).makeNumber(-1);
    RationalFormula c = rmgr.makeNumber("3.4");
    RationalFormula e = rmgr.makeNumber(2147483.647);

    BooleanFormula constraint = bmgr.and(rmgr.greaterOrEquals(a, c), rmgr.greaterOrEquals(c, e));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (>= -1 17/5))\n";
    String expectedResultSMTInterpol = "(assert (and (>= (- 1.0) 3.4) (>= 3.4 2147483.647)))\n";
    String expectedResultCVC4 =
        "(assert (and (>= (- 1) (/ 17 5)) (>= (/ 17 5) (/ 2147483647 1000)" + ")))\n";
    String expectedResultCVC5 =
        "(assert (and (>= (- 1.0) (/ 17 5)) (>= (/ 17 5) (/ 2147483647 " + "1000))))\n";
    String expectedResultZ3 =
        "(assert (and (>= (- 1.0) (/ 17.0 5.0)) (>= (/ 17.0 5.0) (/ " + "2147483647.0 1000.0))))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5)
            || actualResult.equals(expectedResultSMTInterpol)
            || actualResult.equals(expectedResultCVC4)
            || actualResult.equals(expectedResultCVC5)
            || actualResult.equals(expectedResultZ3));
  }

  @Test
  public void testIntegerLessThan() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);

    BooleanFormula constraint = bmgr.and(imgr.lessThan(a, b), imgr.lessThan(c, e));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (< 1 (- 5)))\n";
    String expectedResultOthers = "(assert (and (< 1 (- 5)) (< 3 2147483647)))\n";
    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5) || actualResult.equals(expectedResultOthers));
  }

  @Test
  public void testRationalLessThan() {
    requireRationals();
    clearGenerator();
    RationalFormula a = Objects.requireNonNull(rmgr).makeNumber(-1);
    RationalFormula c = rmgr.makeNumber("3.4");
    RationalFormula e = rmgr.makeNumber(2147483.647);

    BooleanFormula constraint = bmgr.and(rmgr.lessThan(a, c), rmgr.lessThan(c, e));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (< -1 17/5))\n";
    String expectedResultSMTInterpol = "(assert (and (< (- 1.0) 3.4) (< 3.4 2147483.647)))\n";
    String expectedResultCVC4 =
        "(assert (and (< (- 1) (/ 17 5)) (< (/ 17 5) (/ 2147483647 1000)" + ")))\n";
    String expectedResultCVC5 =
        "(assert (and (< (- 1.0) (/ 17 5)) (< (/ 17 5) (/ 2147483647 " + "1000))))\n";
    String expectedResultZ3 =
        "(assert (and (< (- 1.0) (/ 17.0 5.0)) (< (/ 17.0 5.0) (/ " + "2147483647.0 1000.0))))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5)
            || actualResult.equals(expectedResultSMTInterpol)
            || actualResult.equals(expectedResultCVC4)
            || actualResult.equals(expectedResultCVC5)
            || actualResult.equals(expectedResultZ3));
  }

  @Test
  public void testIntegerLessOrEqual() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);

    BooleanFormula constraint = bmgr.and(imgr.lessOrEquals(a, b), imgr.lessOrEquals(c, e));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (<= 1 (- 5)))\n";
    String expectedResultOthers = "(assert (and (<= 1 (- 5)) (<= 3 2147483647)))\n";
    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5) || actualResult.equals(expectedResultOthers));
  }

  @Test
  public void testRationalLessOrEqual() {
    requireRationals();
    clearGenerator();
    RationalFormula a = Objects.requireNonNull(rmgr).makeNumber(-1);
    RationalFormula c = rmgr.makeNumber("3.4");
    RationalFormula e = rmgr.makeNumber(2147483.647);

    BooleanFormula constraint = bmgr.and(rmgr.lessOrEquals(a, c), rmgr.lessOrEquals(c, e));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (<= -1 17/5))\n";
    String expectedResultSMTInterpol = "(assert (and (<= (- 1.0) 3.4) (<= 3.4 2147483.647)))\n";
    String expectedResultCVC4 =
        "(assert (and (<= (- 1) (/ 17 5)) (<= (/ 17 5) (/ 2147483647 1000)" + ")))\n";
    String expectedResultCVC5 =
        "(assert (and (<= (- 1.0) (/ 17 5)) (<= (/ 17 5) (/ 2147483647 " + "1000))))\n";
    String expectedResultZ3 =
        "(assert (and (<= (- 1.0) (/ 17.0 5.0)) (<= (/ 17.0 5.0) (/ " + "2147483647.0 1000.0))))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5)
            || actualResult.equals(expectedResultSMTInterpol)
            || actualResult.equals(expectedResultCVC4)
            || actualResult.equals(expectedResultCVC5)
            || actualResult.equals(expectedResultZ3));
  }

  @Test
  public void testIntegerFloor() {
    requireIntegers();
    clearGenerator();
    IntegerFormula a = imgr.makeNumber(1);
    IntegerFormula b = imgr.makeNumber(-5);
    IntegerFormula c = imgr.makeNumber("3");
    IntegerFormula e = imgr.makeNumber(2147483647);
    BooleanFormula constraint =
        imgr.equal(
            imgr.subtract(imgr.floor(b), imgr.floor(a)),
            imgr.subtract(imgr.floor(c), imgr.floor(e)));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResult = "(assert (= (- (- 5) 1) (- 3 2147483647)))\n";
    Assert.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testRationalFloor() {
    requireRationals();
    clearGenerator();
    RationalFormula a = Objects.requireNonNull(rmgr).makeNumber(-1);
    RationalFormula c = rmgr.makeNumber("3.4");
    RationalFormula e = rmgr.makeNumber(2147483.647);
    BooleanFormula constraint =
        imgr.equal(rmgr.floor(a), imgr.subtract(rmgr.floor(c), rmgr.floor(e)));
    Generator.assembleConstraint(constraint);
    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 = "(assert (= -1 (- (to_int 17/5) (to_int 2147483647/1000))))\n";
    String expectedResultSMTInterpol =
        "(assert (= (to_int (- 1.0)) (- (to_int 3.4) (to_int 2147483.647))))\n";
    String expectedResultCVC4 =
        "(assert (= (to_int (- 1)) (- (to_int (/ 17 5)) (to_int (/ " + "2147483647 1000)))))\n";
    String expectedResultCVC5 =
        "(assert (= (to_int (- 1.0)) (- (to_int (/ 17 5)) (to_int (/ 2147483647 1000)))))\n";
    String expectedResultZ3 =
        "(assert (= (to_int (- 1.0)) (- (to_int (/ 17.0 5.0)) (to_int (/ 2147483647.0"
            + " 1000.0)))))\n";

    Assert.assertTrue(
        actualResult.equals(expectedResultMathsat5)
            || actualResult.equals(expectedResultSMTInterpol)
            || actualResult.equals(expectedResultCVC4)
            || actualResult.equals(expectedResultCVC5)
            || actualResult.equals(expectedResultZ3));
  }
}
