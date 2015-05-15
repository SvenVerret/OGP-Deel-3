package program.expression.logic;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class NotExpression extends Expression{

	/**
	 * 
	 * @param expr
	 * @param sourceLocation
	 */
	public NotExpression(Expression expr, SourceLocation sourceLocation){
		super(sourceLocation);
	}
}
