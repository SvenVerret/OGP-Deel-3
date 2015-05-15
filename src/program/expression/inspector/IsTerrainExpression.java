package program.expression.inspector;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class IsTerrainExpression extends Inspector{

	/**
	 * 
	 * @param expr
	 *  
	 */
	public IsTerrainExpression(Expression expr, SourceLocation sourceLocation){
		super(expr, sourceLocation);
	}
}
