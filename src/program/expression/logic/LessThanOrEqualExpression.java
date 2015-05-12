package program.expression.logic;

import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class LessThanOrEqualExpression extends Logic{

	/**
	 * 
	 * @param left
	 * @param right
	 *  
	 */
	public LessThanOrEqualExpression(Expression left, Expression right, 
			SourceLocation sourceLocation){
		super(left, right, sourceLocation);
	}
}
