package program.expression.operation;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class MultiplicationExpression extends DoubleExpressionOperation{

	/**
	 * 
	 * @param left
	 * @param right
	 *  
	 */
	public MultiplicationExpression(Expression left, Expression right, 
			SourceLocation sourceLocation){
		super(left, right, sourceLocation);
	}
}
