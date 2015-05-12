package program.expression.inspector;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class IsMovingExpression extends Inspector{

	/**
	 * 
	 * @param expr
	 * @param direction
	 *  
	 */
	public IsMovingExpression(Expression expr, Expression direction, 
			SourceLocation sourceLocation){
		super(expr, sourceLocation);
		dir = direction;
	}
	
	
	
	
	public Expression getDirection() {
		return dir;
	}

	private final Expression dir;
}
