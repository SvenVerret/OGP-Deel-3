package program.expression.inspector;

import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;



public class IsAirExpression extends Inspector{

	/**
	 * 
	 * @param expr
	 *  
	 */
	public IsAirExpression(Expression expr, SourceLocation sourceLocation){
		super(expr, sourceLocation);
	}
}
