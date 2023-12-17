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

package org.sosy_lab.java_smt.basicimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.basicimpl.Generator.keyword;

public class BooleanGenerator {

  protected static void logMakeVariable(Object result, String pVar) {
    List<Object> inputParams = new ArrayList<>();
    inputParams.add(pVar);
    Function<List<Object>, String> functionToString =
        inPlaceInputParams -> (String) inPlaceInputParams.get(0);
    Generator.executedAggregator.add(
        new FunctionEnvironment(result, inputParams, functionToString, keyword.BOOL));
  }

  protected static void logMakeTrue(Object result, String pVar) {
    List<Object> inputParams = new ArrayList<>();
    inputParams.add(pVar);
    Function<List<Object>, String> functionToString =
        inPlaceInputParams -> (String) inPlaceInputParams.get(0);
    Generator.executedAggregator.add(
        new FunctionEnvironment(result, inputParams, functionToString, keyword.DIRECT));
  }

  protected static void logMakeFalse(Object result, String pVar) {
    List<Object> inputParams = new ArrayList<>();
    inputParams.add(pVar);
    Function<List<Object>, String> functionToString =
        inPlaceInputParams -> (String) inPlaceInputParams.get(0);
    Generator.executedAggregator.add(
        new FunctionEnvironment(result, inputParams, functionToString, keyword.DIRECT));
  }

  protected static void logNot(Object result, BooleanFormula pBits) {
    List<Object> inputParams = new ArrayList<>();
    inputParams.add(pBits);
    Function<List<Object>, String> functionToString =
        inPlaceInputParams -> "(not " + inPlaceInputParams.get(0) + ")";
    Generator.executedAggregator.add(
        new FunctionEnvironment(result, inputParams, functionToString, keyword.SKIP));
  }

  protected static void logOr(Object result, BooleanFormula pBits1, BooleanFormula pBits2) {
    List<Object> inputParams = new ArrayList<>();
    inputParams.add(pBits1);
    inputParams.add(pBits2);
    Function<List<Object>, String> functionToString =
        inPlaceInputParams ->
            "(or " + inPlaceInputParams.get(0) + " " + inPlaceInputParams.get(1) + ")";
    Generator.executedAggregator.add(
        new FunctionEnvironment(result, inputParams, functionToString, keyword.SKIP));
  }

  protected static void logOr(Object result, Collection<BooleanFormula> pBits1) {

    List<Object> inputParams = new ArrayList<>(pBits1);
    Function<List<Object>, String> functionToString =
        inPlaceInputParams -> {
          StringBuilder out = new StringBuilder();
          out.append("(or ");
          inPlaceInputParams.forEach(
              (c) -> {
                out.append(c);
                out.append(" ");
              });
          return String.valueOf(out.deleteCharAt(out.length() - 1).append(")"));
        };

    Generator.executedAggregator.add(
        new FunctionEnvironment(result, inputParams, functionToString, keyword.SKIP));
  }

  protected static void logAnd(Object result, BooleanFormula pBits1, BooleanFormula pBits2) {
    List<Object> inputParams = new ArrayList<>();
    inputParams.add(pBits1);
    inputParams.add(pBits2);
    Function<List<Object>, String> functionToString =
        inPlaceInputParams ->
            "(and " + inPlaceInputParams.get(0) + " " + inPlaceInputParams.get(1) + ")";
    Generator.executedAggregator.add(
        new FunctionEnvironment(result, inputParams, functionToString, keyword.SKIP));
  }

  protected static void logAnd(Object result, Collection<BooleanFormula> pBits1) {
    List<Object> inputParams = new ArrayList<>(pBits1);
    Function<List<Object>, String> functionToString =
        inPlaceInputParams -> {
          StringBuilder out = new StringBuilder();
          out.delete(0, out.length());
          out.append("(and ");
          inPlaceInputParams.forEach(
              (c) -> {
                out.append(c);
                out.append(" ");
              });
          return String.valueOf(out.deleteCharAt(out.length() - 1).append(")"));
        };

    Generator.executedAggregator.add(
        new FunctionEnvironment(result, inputParams, functionToString, keyword.SKIP));
  }

  protected static void logXor(Object result, BooleanFormula pBits1, BooleanFormula pBits2) {
    List<Object> inputParams = new ArrayList<>();
    inputParams.add(pBits1);
    inputParams.add(pBits2);
    Function<List<Object>, String> functionToString =
        inPlaceInputParams ->
            "(xor " + inPlaceInputParams.get(0) + " " + inPlaceInputParams.get(1) + ")";
    Generator.executedAggregator.add(
        new FunctionEnvironment(result, inputParams, functionToString, keyword.SKIP));
  }

  protected static void logEquivalence(
      Object result, BooleanFormula pBits1, BooleanFormula pBits2) {
    List<Object> inputParams = new ArrayList<>();
    inputParams.add(pBits1);
    inputParams.add(pBits2);
    Function<List<Object>, String> functionToString =
        inPlaceInputParams ->
            "(= " + inPlaceInputParams.get(0) + " " + inPlaceInputParams.get(1) + ")";
    Generator.executedAggregator.add(
        new FunctionEnvironment(result, inputParams, functionToString, keyword.SKIP));
  }

  protected static void logImplication(
      Object result, BooleanFormula pBits1, BooleanFormula pBits2) {
    List<Object> inputParams = new ArrayList<>();
    inputParams.add(pBits1);
    inputParams.add(pBits2);
    Function<List<Object>, String> functionToString =
        inPlaceInputParams ->
            "(=> " + inPlaceInputParams.get(0) + " " + inPlaceInputParams.get(1) + ")";
    Generator.executedAggregator.add(
        new FunctionEnvironment(result, inputParams, functionToString, keyword.SKIP));
  }

  protected static void logIfThenElse(Object result, BooleanFormula pBits1, Object f1, Object f2) {
    List<Object> inputParams = new ArrayList<>();
    inputParams.add(pBits1);
    inputParams.add(f1);
    inputParams.add(f2);
    Function<List<Object>, String> functionToString =
        inPlaceInputParams ->
            "(ite "
                + inPlaceInputParams.get(0)
                + " "
                + inPlaceInputParams.get(1)
                + " "
                + inPlaceInputParams.get(2)
                + ")";
    Generator.executedAggregator.add(
        new FunctionEnvironment(result, inputParams, functionToString, keyword.SKIP));
  }
}
