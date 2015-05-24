package program.expression.operation;

import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;

public class EqualsExpression<R,E1,E2> extends Expression<R> {

	public EqualsExpression(Expression<E1> e1,Expression<E2> e2, SourceLocation sourcelocation){
		super(sourcelocation);
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public Expression<E1> getE1(){
		return e1;
	}
	public Expression<E2> getE2(){
		return e2;
	}

	@Override
	public Boolean evaluate(Program program){
		Object Value1 = getE1().evaluate(program);
		Object Value2 = getE2().evaluate(program);
		if(Value1 != null && Value2 != null){
			return Value1.equals(Value2);
		}else{
			return (Value1 == null && Value2 == null);
		}
		
	}

	private Expression<E1> e1;
	private Expression<E2> e2;

}
