package program.expression.inspector;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class IsDuckingExpression extends Inspector{

	/**
	 * 
	 * @param expr
	 *  
	 */
	public IsDuckingExpression(Expression expr, SourceLocation sourceLocation) {
		super(expr, sourceLocation);
	}
}
