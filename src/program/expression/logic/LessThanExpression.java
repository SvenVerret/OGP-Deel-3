package program.expression.logic;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class LessThanExpression extends Logic{

	/**
	 * 
	 * @param left
	 * @param right
	 *  
	 */
	public LessThanExpression(Expression left, Expression right, 
			SourceLocation sourceLocation){
		super(left, right, sourceLocation);
	}
}
