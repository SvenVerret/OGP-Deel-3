package program.expression.inspector;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class IsWaterExpression extends Inspector{

	/**
	 * 
	 * @param expr
	 *  
	 */
	public IsWaterExpression(Expression expr, SourceLocation sourceLocation){
		super(expr, sourceLocation);
	}
}
