package program.expression.operation;

import java.util.function.BiFunction;

import program.Program;
import program.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class BinaryExpressionOperation<R,E1,E2> extends Expression<R>{

	public BinaryExpressionOperation(Expression<E1> e1,Expression<E2> e2, 
			BiFunction<Object,Object,R> f, SourceLocation sourcelocation) {
		super(sourcelocation);
		this.e1 = e1;
		this.e2 = e2;
		function = f;
	}

	private Expression<E1> e1;
	private Expression<E2> e2;
	private BiFunction<Object,Object,R> function;
	
	public Expression<E1> getE1(){
		return e1;
	}
	public Expression<E2> getE2(){
		return e2;
	}
	
	@Override
	public Object evaluate(Program program){
		return this.function.apply(getE1().evaluate(program),getE2().evaluate(program));
	}
	

}
