package program.expression.operation;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class SqrtExpression extends SingleExpressionOperation{

	/**
	 * 
	 * @param expr
	 *  
	 */
	public SqrtExpression(Expression expr, SourceLocation sourceLocation){
		super(expr, sourceLocation);
		
	}
}
