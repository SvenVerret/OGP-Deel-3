package program.expression.operation;

import java.util.function.BiFunction;
import java.util.function.Function;

import program.expression.Expression;
import program.expression.SingleValueExpression;
import jumpingalien.part3.programs.SourceLocation;

public class BinaryExpressionOperation<T,E1,E2> extends SingleValueExpression<T>{

	public BinaryExpressionOperation(T e1, T e2, BiFunction<T,T,T> f, SourceLocation sourcelocation) {
		super(null,sourcelocation);
		value1 = e1;
		value2 = e2;
		function = f;
		
		super.setValue(function.apply(e1., e2));
	}

	public Expression<E1> getValue1(){
		return value1;
	}
	public Expression<E2> getValue2(){
		return value2;
	}
	

	private Expression<E1> value1;
	private Expression<E2> value2;
	private BiFunction<T,T,T> function;

}
