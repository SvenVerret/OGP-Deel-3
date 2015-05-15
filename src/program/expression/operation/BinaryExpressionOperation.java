package program.expression.operation;

import java.util.function.BiFunction;

import program.expression.Expression;
import program.expression.ValueExpression;
import jumpingalien.part3.programs.SourceLocation;

public class BinaryExpressionOperation<R,E1,E2> extends Expression<R>{

	public BinaryExpressionOperation(ValueExpression<E1> e1, ValueExpression<E2> e2, BiFunction<E1,E2,R> f, SourceLocation sourcelocation) {
		super(sourcelocation);
		this.e1 = e1;
		this.e2 = e2;
		function = f;
	}

	private ValueExpression<E1> e1;
	private ValueExpression<E2> e2;
	private BiFunction<E1,E2,R> function;
	
	public ValueExpression<E1> getE1(){
		return e1;
	}
	public ValueExpression<E2> getE2(){
		return e2;
	}
	
	public R evaluate(){
		return this.function.apply(this.getE1().evaluate(),this.getE2().evaluate());
	}
	

}
