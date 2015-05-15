package program.expression.inspector;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class IsDeadExpression extends Inspector{

	/**
	 * 
	 * @param expr
	 *  
	 */
	public IsDeadExpression(Expression expr, SourceLocation sourceLocation){
		super(expr, sourceLocation);
	}
}
