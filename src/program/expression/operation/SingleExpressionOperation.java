package program.expression.operation;

import java.util.Map;
import java.util.function.Function;

import program.expression.Expression;
import program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public class SingleExpressionOperation<R,E> extends Expression<R>{

	public SingleExpressionOperation(Expression<E> expr, Function<E,R> f, SourceLocation sourcelocation) {
		super(sourcelocation);
		this.e = expr;
		function = f;
	}

	private Expression<E> e;
	private Function<E,R> function;

	public Expression<E> getE(){
		return e;
	}

	@Override
	public R evaluate(Map<String, Type> globalVariables){
		return this.function.apply(this.getE().evaluate(globalVariables));
	}
}
