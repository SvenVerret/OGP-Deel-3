package program.expression.logic;



import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;
import program.type.BooleanType;

public class AndExpression extends Logic{

	/**
	 * 
	 * @param left
	 * @param right
	 *  
	 */
	public AndExpression(Expression left, Expression right, SourceLocation sourcelocation){
		super(left,right, sourcelocation);
	}
	
	public BooleanType getResult(){
		if ((boolean)this.getLeft)
			return true;
			

	}
}
