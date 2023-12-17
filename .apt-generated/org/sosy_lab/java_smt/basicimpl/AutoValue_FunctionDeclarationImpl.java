package org.sosy_lab.java_smt.basicimpl;

import com.google.common.collect.ImmutableList;
import javax.annotation.processing.Generated;
import org.sosy_lab.java_smt.api.Formula;
import org.sosy_lab.java_smt.api.FormulaType;
import org.sosy_lab.java_smt.api.FunctionDeclarationKind;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_FunctionDeclarationImpl<F extends Formula, T> extends FunctionDeclarationImpl<F, T> {

  private final FunctionDeclarationKind kind;

  private final String name;

  private final FormulaType<F> type;

  private final ImmutableList<FormulaType<?>> argumentTypes;

  private final T solverDeclaration;

  AutoValue_FunctionDeclarationImpl(
      FunctionDeclarationKind kind,
      String name,
      FormulaType<F> type,
      ImmutableList<FormulaType<?>> argumentTypes,
      T solverDeclaration) {
    if (kind == null) {
      throw new NullPointerException("Null kind");
    }
    this.kind = kind;
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    if (type == null) {
      throw new NullPointerException("Null type");
    }
    this.type = type;
    if (argumentTypes == null) {
      throw new NullPointerException("Null argumentTypes");
    }
    this.argumentTypes = argumentTypes;
    if (solverDeclaration == null) {
      throw new NullPointerException("Null solverDeclaration");
    }
    this.solverDeclaration = solverDeclaration;
  }

  @Override
  public FunctionDeclarationKind getKind() {
    return kind;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public FormulaType<F> getType() {
    return type;
  }

  @Override
  public ImmutableList<FormulaType<?>> getArgumentTypes() {
    return argumentTypes;
  }

  @Override
  public T getSolverDeclaration() {
    return solverDeclaration;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof FunctionDeclarationImpl) {
      FunctionDeclarationImpl<?, ?> that = (FunctionDeclarationImpl<?, ?>) o;
      return this.kind.equals(that.getKind())
          && this.name.equals(that.getName())
          && this.type.equals(that.getType())
          && this.argumentTypes.equals(that.getArgumentTypes())
          && this.solverDeclaration.equals(that.getSolverDeclaration());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= kind.hashCode();
    h$ *= 1000003;
    h$ ^= name.hashCode();
    h$ *= 1000003;
    h$ ^= type.hashCode();
    h$ *= 1000003;
    h$ ^= argumentTypes.hashCode();
    h$ *= 1000003;
    h$ ^= solverDeclaration.hashCode();
    return h$;
  }

}
