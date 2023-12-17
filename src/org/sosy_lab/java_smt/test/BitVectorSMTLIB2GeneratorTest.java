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

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.TruthJUnit.assume;

import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import org.sosy_lab.java_smt.SolverContextFactory.Solvers;
import org.sosy_lab.java_smt.api.BitvectorFormula;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.FormulaType;
import org.sosy_lab.java_smt.basicimpl.Generator;

public class BitVectorSMTLIB2GeneratorTest extends SolverBasedTest0.ParameterizedSolverBasedTest0 {

  /**
   * Integer and Rationals not supported by BOOLECTOR Rationals not supported by PRINCESS Z3 runs
   * only when executed separately from other solvers
   */
  public void clearGenerator() {
    Generator.setIsLoggingEnabled(true);
    Generator.lines.delete(0, Generator.lines.length());
    Generator.registeredVariables.clear();
    Generator.executedAggregator.clear();
  }

  @Test
  public void testMakeVariable() {
    requireBitvectors();
    clearGenerator();
    BitvectorFormula a = Objects.requireNonNull(bvmgr).makeVariable(32, "a");
    BitvectorFormula b = bvmgr.makeVariable(32, "b");
    BitvectorFormula c = bvmgr.makeVariable(FormulaType.getBitvectorTypeWithSize(5), "c");
    BitvectorFormula d = bvmgr.makeVariable(FormulaType.getBitvectorTypeWithSize(5), "d");
    BooleanFormula constraint1 = bvmgr.equal(a, bvmgr.add(a, b));
    BooleanFormula constraint2 = bvmgr.equal(c, d);

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint2);

    /*
     avoid such high numbers with Boolector and Princess
    BitvectorFormula e = bvmgr.makeVariable(214748366, "e");
    BitvectorFormula f = bvmgr.makeVariable(214748366, "f");
    BooleanFormula constraint3 = bvmgr.equal(e, f);
    Generator.assembleConstraint(constraint3);
    */

    String actualResult = String.valueOf(Generator.lines);

    String expectedResult =
        "(declare-const a (_ BitVec 32))\n"
            + "(declare-const b (_ BitVec 32))\n"
            + "(assert (= a (bvadd a b)))\n"
            + "(declare-const c (_ BitVec 5))\n"
            + "(declare-const d (_ BitVec 5))\n"
            + "(assert (= c d))\n";
    // + "(declare-const e (_ BitVec 214748366))\n"
    // + "(declare-const f (_ BitVec 214748366))\n"
    // + "(assert (= e f))\n";

    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test
  public void testMakeBitVectorWithIntegerFormulas() {
    requireBitvectors();
    assume()
        .that(solverToUse())
        .isNotEqualTo(Solvers.Z3);
    requireIntegers();
    requireBitvectorToInt();
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula a = bvmgr.makeBitvector(5, imgr.makeNumber(10));
    BitvectorFormula b = bvmgr.makeBitvector(5, imgr.makeNumber(10));
    BitvectorFormula e = Objects.requireNonNull(bvmgr).makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 263255258);
    BooleanFormula constraint1 = bvmgr.equal(c, d);
    BooleanFormula constraint2 = bvmgr.equal(a, b);
    BooleanFormula constraint3 = bvmgr.equal(e, f);

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint2);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(assert (= #b111111110110 #b000000010100))\n"
            + "(assert (= #b01010 #b01010))\n"
            + "(assert (= #b111111110110 #b000000010100))\n";
    String expectedResultOthers =
        "(assert (= #b111111110110 #b000000010100))\n"
            + "(assert (= #b01010 #b01010))\n"
            + "(assert (="
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011011010))\n";
    Assert.assertTrue(
        expectedResultMathsat5.equals(actualResult) || expectedResultOthers.equals(actualResult));
  }

  @Test
  public void testMakeBitVectorWithoutIntegerFormulas() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula a = bvmgr.makeBitvector(5, 10);
    BitvectorFormula b = bvmgr.makeBitvector(5, 10);
    BitvectorFormula e = Objects.requireNonNull(bvmgr).makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 263255258);
    BooleanFormula constraint1 = bvmgr.equal(c, d);
    BooleanFormula constraint2 = bvmgr.equal(a, b);
    BooleanFormula constraint3 = bvmgr.equal(e, f);

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint2);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(assert (= #b111111110110 #b000000010100))\n"
            + "(assert (= #b01010 #b01010))\n"
            + "(assert (= #b111111110110 #b000000010100))\n";
    String expectedResultOthers =
        "(assert (= #b111111110110 #b000000010100))\n"
            + "(assert (= #b01010 #b01010))\n"
            + "(assert (="
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011011010))\n";
    Assert.assertTrue(
        expectedResultMathsat5.equals(actualResult) || expectedResultOthers.equals(actualResult));
  }

  @Test
  public void testAdd() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula a = bvmgr.makeBitvector(5, 10);
    BitvectorFormula b = bvmgr.makeBitvector(5, 0);
    BitvectorFormula e = Objects.requireNonNull(bvmgr).makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 263255258);
    BooleanFormula constraint1 = bvmgr.equal(c, bvmgr.add(c, d));
    BooleanFormula constraint2 = bvmgr.equal(a, bvmgr.add(a, b));
    BooleanFormula constraint3 = bvmgr.equal(e, bvmgr.add(e, f));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint2);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(assert (= #b111111110110 (bvadd #b111111110110 #b000000010100)))\n"
            + "(assert (= #b01010 #b01010))\n"
            + "(assert (= #b111111110110 (bvadd #b111111110110 #b000000010100)))\n";
    String expectedResultOthers =
        "(assert (= #b111111110110 (bvadd #b111111110110 #b000000010100)))\n"
            + "(assert (= #b01010 (bvadd #b01010 #b00000)))\n"
            + "(assert (="
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " (bvadd"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011011010)))\n";
    Assert.assertTrue(
        expectedResultMathsat5.equals(actualResult) || expectedResultOthers.equals(actualResult));
  }

  @Test
  public void testNegate() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = Objects.requireNonNull(bvmgr).makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 =
        bvmgr.equal(bvmgr.negate(c), bvmgr.add(bvmgr.negate(c), bvmgr.negate(d)));
    BooleanFormula constraint3 =
        bvmgr.equal(bvmgr.negate(e), bvmgr.add(bvmgr.negate(e), bvmgr.negate(f)));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(assert (= (bvneg #b111111110110) #b111111110110))\n"
            + "(assert (= (bvneg"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110)"
            + " (bvneg"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110)))\n";
    String expectedResultOthers =
        "(assert (= (bvneg #b111111110110) (bvadd (bvneg #b111111110110) (bvneg"
            + " #b000000010100))))\n"
            + "(assert (= (bvneg"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110)"
            + " (bvadd (bvneg"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110)"
            + " (bvneg"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000))))\n";
    Assert.assertTrue(
        expectedResultMathsat5.equals(actualResult) || expectedResultOthers.equals(actualResult));
  }

  @Test
  public void testSubtract() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = bvmgr.makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(c, bvmgr.subtract(c, d));
    BooleanFormula constraint3 = bvmgr.equal(e, bvmgr.subtract(e, f));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(assert (= #b111111110110 (bvsub #b111111110110 #b000000010100)))\n"
            + "(assert (="
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110))\n";
    String expectedResultOthers =
        "(assert (= #b111111110110 (bvsub #b111111110110 #b000000010100)))\n"
            + "(assert (="
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " (bvsub"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    Assert.assertTrue(
        expectedResultMathsat5.equals(actualResult) || expectedResultOthers.equals(actualResult));
  }

  @Test
  public void testDivide() {
    // Does not work for CVC4 due to "BigInteger argument out of bounds"
    requireBitvectors();
    assume()
        .withMessage("Solver %s cannot handle this BigInterger argument", solverToUse())
        .that(solverToUse())
        .isNotEqualTo(Solvers.CVC4);
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = bvmgr.makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(c, bvmgr.divide(c, d, true));
    BooleanFormula constraint3 = bvmgr.equal(e, bvmgr.divide(e, f, false));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultOthers =
        "(assert (= #b111111110110 (bvsdiv #b111111110110 #b000000010100)))\n"
            + "(assert (="
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " (bvudiv"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    String expectedResultYices =
        "(assert (= #b111111110110 (bvsdiv #b111111110110 "
            + "#b000000010100)))\n"
            + "(assert (= #b111111110110 (bvsdiv #b111111110110 #b000000010100)))\n";
    Assert.assertTrue(
        expectedResultYices.equals(actualResult) || expectedResultOthers.equals(actualResult));
  }

  @Test
  public void testModulo() {
    // Does not work for CVC4 due to "BigInteger argument out of bounds"
    requireBitvectors();
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = bvmgr.makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(c, bvmgr.modulo(c, d, true));
    BooleanFormula constraint3 = bvmgr.equal(e, bvmgr.modulo(e, f, false));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultOthers =
        "(assert (= #b111111110110 (bvsrem #b111111110110 #b000000010100)))\n"
            + "(assert (="
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " (bvurem"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    String expectedResultYices =
        "(assert (= #b111111110110 #b111111110110))\n"
            + "(assert (= #b111111110110 #b111111110110))\n";
    String expectedResultMathsat5 =
        "(assert (= #b111111110110 #b111111110110))\n"
            + "(assert (="
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " (bvurem"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    Assert.assertTrue(
        expectedResultYices.equals(actualResult)
            || expectedResultOthers.equals(actualResult)
            || expectedResultMathsat5.equals(actualResult));
  }

  @Test
  public void testMultiply() {
    // Does not work for CVC4 due to "BigInteger argument out of bounds"
    requireBitvectors();
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = bvmgr.makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(c, bvmgr.multiply(c, d));
    BooleanFormula constraint3 = bvmgr.equal(e, bvmgr.multiply(e, f));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultOthers =
        "(assert (= #b111111110110 (bvmul #b111111110110 #b000000010100)))\n"
            + "(assert (="
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " (bvmul"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    String expectedResultYices =
        "(assert (= #b111111110110 #b111111110110))\n"
            + "(assert (= #b111111110110 #b111111110110))\n";
    String expectedResultMathsat5 =
        "(assert (= #b111111110110 (bvmul #b111111110110 #b000000010100)))\n"
            + "(assert (= #b111111110110 (bvmul #b111111110110 #b000000010100)))\n";
    Assert.assertTrue(
        expectedResultYices.equals(actualResult)
            || expectedResultOthers.equals(actualResult)
            || expectedResultMathsat5.equals(actualResult));
  }

  @Test
  public void testGreaterThan() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = bvmgr.makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.greaterThan(c, d, true);
    BooleanFormula constraint3 = bvmgr.greaterThan(e, f, false);

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResult =
        "(assert (bvsgt #b111111110110 #b000000010100))\n"
            + "(assert (bvugt"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000))\n";
    Assert.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testGreaterOrEqual() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = bvmgr.makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.greaterOrEquals(c, d, true);
    BooleanFormula constraint3 = bvmgr.greaterOrEquals(e, f, false);

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResult =
        "(assert (bvsge #b111111110110 #b000000010100))\n"
            + "(assert (bvuge"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000))\n";
    Assert.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testLessThan() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = bvmgr.makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.lessThan(c, d, true);
    BooleanFormula constraint3 = bvmgr.lessThan(e, f, false);

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResult =
        "(assert (bvslt #b111111110110 #b000000010100))\n"
            + "(assert (bvult"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000))\n";
    Assert.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testLessOrEqual() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = bvmgr.makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.lessOrEquals(c, d, true);
    BooleanFormula constraint3 = bvmgr.lessOrEquals(e, f, false);

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResult =
        "(assert (bvsle #b111111110110 #b000000010100))\n"
            + "(assert (bvule"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000))\n";
    Assert.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testNot() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = Objects.requireNonNull(bvmgr).makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(bvmgr.not(c), bvmgr.not(d));
    BooleanFormula constraint3 = bvmgr.equal(bvmgr.not(e), bvmgr.not(f));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(assert (= (bvnot #b111111110110) (bvnot #b000000010100)))\n"
            + "(assert (= (bvnot #b111111110110) (bvnot #b000000010100)))\n";
    String expectedResultOthers =
        "(assert (= (bvnot #b111111110110) (bvnot #b000000010100)))\n"
            + "(assert (= (bvnot"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110)"
            + " (bvnot"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    Assert.assertTrue(
        expectedResultMathsat5.equals(actualResult) || expectedResultOthers.equals(actualResult));
  }

  @Test
  public void testAnd() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula a = Objects.requireNonNull(bvmgr).makeVariable(12, "a");
    BitvectorFormula b = bvmgr.makeVariable(100, "b");
    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = Objects.requireNonNull(bvmgr).makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(a, bvmgr.and(c, d));
    BooleanFormula constraint3 = bvmgr.equal(b, bvmgr.and(e, f));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(declare-const a (_ BitVec 12))\n"
            + "(assert (= a #b000000010100))\n"
            + "(declare-const b (_ BitVec 100))\n"
            + "(assert (= b"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000))\n";
    String expectedResultZ3 =
        "(declare-const a (_ BitVec 12))\n"
            + "(assert (= a (bvand #b111111110110 #b000000010100)))\n"
            + "(declare-const b (_ BitVec 100))\n"
            + "(assert (= b (bvand"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    Assert.assertTrue(
        expectedResultMathsat5.equals(actualResult) || expectedResultZ3.equals(actualResult));
  }

  @Test
  public void testOr() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula a = Objects.requireNonNull(bvmgr).makeVariable(12, "a");
    BitvectorFormula b = bvmgr.makeVariable(100, "b");
    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = Objects.requireNonNull(bvmgr).makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(a, bvmgr.or(c, d));
    BooleanFormula constraint3 = bvmgr.equal(b, bvmgr.or(e, f));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(declare-const a (_ BitVec 12))\n"
            + "(assert (= a #b111111110110))\n"
            + "(declare-const b (_ BitVec 100))\n"
            + "(assert (= b"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110))\n";
    String expectedResultZ3 =
        "(declare-const a (_ BitVec 12))\n"
            + "(assert (= a (bvor #b111111110110 #b000000010100)))\n"
            + "(declare-const b (_ BitVec 100))\n"
            + "(assert (= b (bvor"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    Assert.assertTrue(
        expectedResultMathsat5.equals(actualResult) || expectedResultZ3.equals(actualResult));
  }

  @Test
  public void testXor() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula a = Objects.requireNonNull(bvmgr).makeVariable(12, "a");
    BitvectorFormula b = bvmgr.makeVariable(100, "b");
    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = Objects.requireNonNull(bvmgr).makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(a, bvmgr.xor(c, d));
    BooleanFormula constraint3 = bvmgr.equal(b, bvmgr.xor(e, f));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(declare-const a (_ BitVec 12))\n"
            + "(assert (= a (bvxor #b111111110110 #b000000010100)))\n"
            + "(declare-const b (_ BitVec 100))\n"
            + "(assert (= b"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110))\n";
    String expectedResultZ3 =
        "(declare-const a (_ BitVec 12))\n"
            + "(assert (= a (bvxor #b111111110110 #b000000010100)))\n"
            + "(declare-const b (_ BitVec 100))\n"
            + "(assert (= b (bvxor"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    Assert.assertTrue(
        expectedResultMathsat5.equals(actualResult) || expectedResultZ3.equals(actualResult));
  }

  @Test
  public void testShiftRight() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula a = Objects.requireNonNull(bvmgr).makeVariable(12, "a");
    BitvectorFormula b = bvmgr.makeVariable(100, "b");
    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = Objects.requireNonNull(bvmgr).makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(a, bvmgr.shiftRight(c, d, true));
    BooleanFormula constraint3 = bvmgr.equal(b, bvmgr.shiftRight(e, f, false));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(declare-const a (_ BitVec 12))\n"
            + "(assert (= a (bvashr #b111111110110 #b000000010100)))\n"
            + "(declare-const b (_ BitVec 100))\n"
            + "(assert (= b"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110))\n";
    String expectedResultZ3 =
        "(declare-const a (_ BitVec 12))\n"
            + "(assert (= a (bvashr #b111111110110 #b000000010100)))\n"
            + "(declare-const b (_ BitVec 100))\n"
            + "(assert (= b (bvlshr"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    Assert.assertTrue(
        expectedResultMathsat5.equals(actualResult) || expectedResultZ3.equals(actualResult));
  }

  @Test
  public void testShiftLeft() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula a = Objects.requireNonNull(bvmgr).makeVariable(12, "a");
    BitvectorFormula b = bvmgr.makeVariable(100, "b");
    BitvectorFormula c = Objects.requireNonNull(bvmgr).makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = Objects.requireNonNull(bvmgr).makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(a, bvmgr.shiftLeft(c, d));
    BooleanFormula constraint3 = bvmgr.equal(b, bvmgr.shiftLeft(e, f));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultOthers =
        "(declare-const a (_ BitVec 12))\n"
            + "(assert (= a (bvshl #b111111110110 #b000000010100)))\n"
            + "(declare-const b (_ BitVec 100))\n"
            + "(assert (= b (bvshl"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    String expectedResultMathsat5 =
        "(declare-const a (_ BitVec 12))\n"
            + "(assert (= a (bvshl #b111111110110 #b000000010100)))\n"
            + "(declare-const b (_ BitVec 100))\n"
            + "(assert (= b"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110))\n";
    Assert.assertTrue(
        expectedResultOthers.equals(actualResult) || expectedResultMathsat5.equals(actualResult));
  }

  @Test
  public void testConcat() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula a = Objects.requireNonNull(bvmgr).makeVariable(24, "a");
    BitvectorFormula b = bvmgr.makeVariable(200, "b");
    BitvectorFormula c = bvmgr.makeBitvector(12, -10);
    BitvectorFormula d = bvmgr.makeBitvector(12, 20);
    BitvectorFormula e = bvmgr.makeBitvector(100, 263255254);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(a, bvmgr.concat(c, d));
    BooleanFormula constraint3 = bvmgr.equal(b, bvmgr.concat(e, f));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(declare-const a (_ BitVec 24))\n"
            + "(assert (= a (concat #b111111110110 #b000000010100)))\n"
            + "(declare-const b (_ BitVec 200))\n"
            + "(assert (= b (concat"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000001111101100001111010011010110"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    Assert.assertEquals(expectedResultMathsat5, actualResult);
  }

  @Test
  public void testExtract() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula a = Objects.requireNonNull(bvmgr).makeVariable(6, "a");
    BitvectorFormula b = bvmgr.makeVariable(50, "b");
    BitvectorFormula c = bvmgr.makeBitvector(12, -10);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(a, bvmgr.extract(c, 11, 6));
    BooleanFormula constraint3 = bvmgr.equal(b, bvmgr.extract(f, 99, 50));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(declare-const a (_ BitVec 6))\n"
            + "(assert (= a ((_ extract 11 6) #b111111110110)))\n"
            + "(declare-const b (_ BitVec 50))\n"
            + "(assert (= b ((_ extract 99 50)"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    Assert.assertEquals(expectedResultMathsat5, actualResult);
  }

  @Test
  public void testExtend() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula a = Objects.requireNonNull(bvmgr).makeVariable(18, "a");
    BitvectorFormula b = bvmgr.makeVariable(150, "b");
    BitvectorFormula c = bvmgr.makeBitvector(12, -10);
    BitvectorFormula f = bvmgr.makeBitvector(100, 0);
    BooleanFormula constraint1 = bvmgr.equal(a, bvmgr.extend(c, 6, true));
    BooleanFormula constraint3 = bvmgr.equal(b, bvmgr.extend(f, 50, false));

    Generator.assembleConstraint(constraint1);
    Generator.assembleConstraint(constraint3);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultMathsat5 =
        "(declare-const a (_ BitVec 18))\n"
            + "(assert (= a ((_ sign_extend 6) #b111111110110)))\n"
            + "(declare-const b (_ BitVec 150))\n"
            + "(assert (= b ((_ zero_extend 50)"
            + " #b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)))\n";
    Assert.assertEquals(expectedResultMathsat5, actualResult);
  }

  @Test
  public void testNested() {
    requireBitvectors();
    clearGenerator();

    BitvectorFormula a = Objects.requireNonNull(bvmgr).makeVariable(5, "a");
    BitvectorFormula b = bvmgr.makeVariable(5, "b");
    BitvectorFormula c = bvmgr.makeBitvector(5, -10);
    BitvectorFormula f = bvmgr.makeBitvector(5, 0);
    BitvectorFormula term1 = bvmgr.add(a, b);
    BitvectorFormula term2 = bvmgr.divide(c, f, true);
    BitvectorFormula term3 = bvmgr.modulo(a, c, true);
    BitvectorFormula term4 = bvmgr.xor(b, f);
    BitvectorFormula term5 = bvmgr.subtract(term1, term2);
    BitvectorFormula term6 = bvmgr.and(term5, term3);
    BitvectorFormula term7 = bvmgr.shiftLeft(term6, term4);
    BooleanFormula constraint = bvmgr.equal(a, term7);

    Generator.assembleConstraint(constraint);

    String actualResult = String.valueOf(Generator.lines);

    String expectedResultOthers =
        "(declare-const a (_ BitVec 5))\n"
            + "(declare-const b (_ BitVec 5))\n"
            + "(assert (= a (bvshl (bvand (bvsub (bvadd a b) (bvsdiv #b10110 #b00000)) (bvsrem a"
            + " #b10110)) (bvxor b #b00000))))\n";
    String expectedResultMathsat5 =
        "(declare-const a (_ BitVec 5))\n"
            + "(declare-const b (_ BitVec 5))\n"
            + "(assert (= a (bvshl (bvand (bvsub (bvadd a b) (bvsdiv #b10110 #b00000)) (bvsrem a "
            + "#b10110)) b)))\n";
    Assert.assertTrue(
        expectedResultOthers.equals(actualResult) || expectedResultMathsat5.equals(actualResult));
  }
}
