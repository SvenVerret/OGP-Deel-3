package program.expression.getter;

import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;


public class GetHeightExpression extends SingleExpressionGetter{

	/**
	 * 
	 * @param expr
	 *  
	 */
	public GetHeightExpression(Expression expr, SourceLocation sourceLocation){
		super(expr, sourceLocation);
	}
}
