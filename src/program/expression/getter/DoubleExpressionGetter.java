package program.expression.getter;

import program.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class DoubleExpressionGetter extends Getter{

	public DoubleExpressionGetter(Expression e1, Expression e2, 
			SourceLocation sourcelocation) {
		super(sourcelocation);
		x = e1;
		y = e2;
				
		

	}
	
	public Expression getX() {
		return x;
	}

	public Expression getY() {
		return y;
	}

	private final Expression x;
	private final Expression y;
	

}
