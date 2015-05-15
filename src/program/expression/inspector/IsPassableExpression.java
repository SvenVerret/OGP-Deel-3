package program.expression.inspector;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class IsPassableExpression extends Inspector{

	/**
	 * 
	 * @param expr
	 *  
	 */
	public IsPassableExpression(Expression expr, SourceLocation sourceLocation){
		super(expr, sourceLocation);
	}
}
