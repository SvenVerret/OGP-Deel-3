package program.expression.inspector;


import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.expression.TileExpression;

public class IsInpassableExpression extends Inspector<TileExpression>{

	/**
	 * 
	 * @param expr
	 *  
	 */
	public IsInpassableExpression(Expression<?> expr, SourceLocation sourceLocation){
		super(expr, sourceLocation);
	}

	@Override
	public Boolean evaluate(Program program) {
		
		TileExpression value = getExpression();
		value.setReturnToPixels();
		int[] pos = value.evaluate(program); 
	
		return  !program.getGameObject().getWorld().isPassableTerrain(pos[0], pos[1]);
		
	}
}
